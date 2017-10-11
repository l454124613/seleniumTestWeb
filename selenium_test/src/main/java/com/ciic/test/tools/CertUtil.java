package com.ciic.test.tools;

import java.io.FileInputStream;
import java.nio.file.Paths;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

/**
 * Created by lixuecheng on 2017/10/10.
 */
public class CertUtil {
    /**
     * @author God
     * @cerPath Java读取Cer证书信息
     * @throws Exception
     * @return X509Cer对象
     */
    public static X509Certificate getX509CerCate(String cerPath) throws Exception {
        X509Certificate x509Certificate = null;
        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
        FileInputStream fileInputStream = new FileInputStream(cerPath);
        x509Certificate = (X509Certificate) certificateFactory.generateCertificate(fileInputStream);
        fileInputStream.close();
        System.out.println("读取Cer证书信息...");
        System.out.println("x509Certificate_SerialNumber_序列号___:"+x509Certificate.getSerialNumber());
        System.out.println("x509Certificate_getIssuerDN_发布方标识名___:"+x509Certificate.getIssuerDN());
        System.out.println("x509Certificate_getSubjectDN_主体标识___:"+x509Certificate.getSubjectDN());
        System.out.println("x509Certificate_getSigAlgOID_证书算法OID字符串___:"+x509Certificate.getSigAlgOID());
        System.out.println("x509Certificate_getNotBefore_证书有效期___:"+x509Certificate.getNotAfter());
        System.out.println("x509Certificate_getSigAlgName_签名算法___:"+x509Certificate.getSigAlgName());
        System.out.println("x509Certificate_getVersion_版本号___:"+x509Certificate.getVersion());
        System.out.println("x509Certificate_getPublicKey_公钥___:"+x509Certificate.getPublicKey());
        return x509Certificate;
    }
    public static void main(String[] args) throws Exception {
        getX509CerCate(Paths.get(Thread.currentThread().getContextClassLoader().getResource("ca.crt").toURI()).toString());

    }
}
