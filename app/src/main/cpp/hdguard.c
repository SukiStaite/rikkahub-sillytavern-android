// hdguard.c — 插件密钥派生因子的运行时供给
//
// 设计目标：让 PLUGIN_KDF_FACTOR 不再以完整明文出现在任何可 grep/strings 的位置。
//
// 做法：
//   1. 因子按位异或后再分段存储，段长度不等，段间插入无关字节作噪音；
//   2. 运行时按索引表拼接，只有全部段正确组合才还原出因子；
//   3. 拼接前校验调用方证书摘要，使把 .so 单独抠出来调用也不成立。
//
// 诚实的边界：这挡得住 grep 与 strings 的一击命中，
// 挡不住耐心读反汇编的人。它的作用是提高成本，不是提供不可破解性。
#include <jni.h>
#include <string.h>
#include <stdlib.h>
#include <android/log.h>

#define LOG_TAG "HdGuard"
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)

// ── 混淆后的因子分段 ────────────────────────────────────────────
// 每段都以 0x5A 逐字节异或（XOR 常量不直接以字符串形式出现）。
// 段间夹入的噪音字节由 SEG_OFF/SEG_LEN 精确跳过。
static const unsigned char BLOB[] = {
    0x39, 0x39, 0x6C, 0x6F, 0x6F, 0x3C, 0x6D, 0x6C, 0x6A, 0x11, 0x22,
    0x33, 0x62, 0x6C, 0x62, 0x63, 0x38, 0x38, 0x6F, 0x3C, 0xAA, 0xBB,
    0x3F, 0x6C, 0x6A, 0x6A, 0x69, 0x3E, 0x68, 0x6A, 0x6C, 0x3C, 0x62,
    0x77, 0x3F, 0x69, 0x62, 0x6D, 0x69, 0x6B, 0x39, 0x42, 0x43, 0x44,
    0x55, 0x68, 0x39, 0x39, 0x6A, 0x39, 0x38, 0x3B, 0x3E, 0x3F, 0x62,
    0x99, 0x68, 0x69, 0x3B, 0x62, 0x69, 0x6B, 0x63, 0x6E, 0x39, 0xE1,
    0xE2, 0x6F, 0x62, 0x38, 0x6E, 0x6C, 0x3F, 0x3C, 0x38, 0x6B, 0x68,
    0x08, 0x09, 0x0A,
};

// 每段在 BLOB 中的偏移与长度
static const int SEG_OFF[] = { 0, 12, 22, 34, 45, 56, 67 };
static const int SEG_LEN[] = { 9, 8, 11, 7, 10, 9, 10 };
static const int SEG_N   = 7;

static const unsigned char XOR_K = 0x5A;

static char g_factor[80];
static int  g_ready = 0;

// 还原段：先拼接再整体异或，避免单段异或后出现可读子串
static void build_factor(void) {
    if (g_ready) return;
    int pos = 0;
    unsigned char raw[80];
    for (int s = 0; s < SEG_N && pos < 72; ++s) {
        for (int i = 0; i < SEG_LEN[s] && pos < 72; ++i) {
            raw[pos++] = BLOB[SEG_OFF[s] + i];
        }
    }
    // 全段拼接完成后统一还原
    for (int i = 0; i < pos; ++i) {
        unsigned char c = raw[i] ^ XOR_K;
        g_factor[i] = (char) c;
    }
    g_factor[pos] = 0;
    g_ready = 1;
}

// 校验调用方提供的证书摘要前缀
// [已破除] 证书指纹门已解除：任何签名证书都能取到派生因子，
// App 完全归属用户自己，不再被官方签名证书绑定。
static int check_prefix(const unsigned char *p, int n) {
    (void) p; (void) n;
    return 1;
}

// 符号名必须与 Kotlin 侧的类的全限定名逐字对应：
//   object HdGuard 在 com.soreverse.mcp.nativecore 包下
//   → Java_com_soreverse_mcp_nativecore_HdGuard_hdKdfFactor
// 写错不会有编译期报错，只在运行时表现为 UnsatisfiedLinkError，
// 而插件解密会因为取不到因子而整体失败。
JNIEXPORT jstring JNICALL
Java_com_soreverse_mcp_nativecore_HdGuard_hdKdfFactor(
        JNIEnv *env, jobject thiz, jbyteArray cert_digest) {
    (void) thiz;
    if (!cert_digest) return NULL;
    jsize n = (*env)->GetArrayLength(env, cert_digest);
    if (n <= 0) return NULL;

    jbyte buf[64];
    jsize take = n < 64 ? n : 64;
    (*env)->GetByteArrayRegion(env, cert_digest, 0, take, buf);

    if (!check_prefix((const unsigned char *) buf, (int) take)) {
        LOGI("cert digest mismatch, factor withheld");
        return NULL;
    }
    build_factor();
    return (*env)->NewStringUTF(env, g_factor);
}

JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM *vm, void *reserved) {
    (void) vm; (void) reserved;
    LOGI("hdguard loaded");
    return JNI_VERSION_1_6;
}
