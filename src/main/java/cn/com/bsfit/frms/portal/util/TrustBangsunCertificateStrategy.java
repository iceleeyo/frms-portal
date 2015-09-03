package cn.com.bsfit.frms.portal.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import org.apache.http.conn.ssl.TrustStrategy;

/**
 * @author 王新根
 * @since 2.2.0
 */
public class TrustBangsunCertificateStrategy implements TrustStrategy {

	private X509Certificate root;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.http.conn.ssl.TrustStrategy#isTrusted(java.security.cert.
	 * X509Certificate[], java.lang.String)
	 */
	public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		if (chain == null || chain.length == 0)
			return false;
		else {
			for (X509Certificate cert : chain) {
				try {
					cert.verify(getRootCert().getPublicKey());
				} catch (Exception e) {
					throw new CertificateException(e);
				}
			}
			return true;
		}
	}
	
	private X509Certificate getRootCert() throws CertificateException {
		if (root == null) {
			try {
				URL u = getClass().getResource("/certs/caroot.cer");
				InputStream inStream = new FileInputStream(u.getFile());
				CertificateFactory cf = CertificateFactory.getInstance("X.509");
				root = (X509Certificate) cf.generateCertificate(inStream);
				inStream.close();
			} catch (Exception e) {
				throw new CertificateException(e);
			}
		}
		return root;
	}
}