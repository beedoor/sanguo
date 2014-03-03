import java.io.StringWriter;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

public class DoHd {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String str = "12312我abc的中国";
		int len = 10;
		
		StringWriter writer = new StringWriter(len);
		int j=0;
		for (int i = 0; i < len;i++) {
			char c = str.charAt(i);
			if (c > 0x7F) {
				j = j + 3;
			} else {
				j++;
			}
			if(j > len)
			{
				break;
			}
			writer.write(c);
		}
		System.out.println(writer.toString());
	}

	public static void hd() {
		HttpClient httpClient = new HttpClient();
		// 创建GET方法的实例
		PostMethod postMethod = new PostMethod(
				"http://10.79.11.178:9018/syfbs-oms/bankPayCallBack/payout");
		postMethod.addRequestHeader("Content-type",
				"application/x-www-form-urlencoded; charset=UTF-8");
		postMethod.addRequestHeader("Cache-Control", "no-cache");
		postMethod.addRequestHeader("Pragma", "no-cache");
		postMethod.addRequestHeader("Accept-Encoding", "gzip");
		postMethod.addRequestHeader("Connection", "Keep-Alive");
		postMethod
				.addRequestHeader(
						"User-Agent",
						"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.117 Safari/537.36");
		// postMethod.addRequestHeader("Cookie","ADMINCONSOLESESSION=bngdTKpL5RdYPMq4qVxQVJ2qyvVt07JfBGwCwy3dmrT2NkjKvvW2!-565870577; JSESSIONID_FBS_OMS=t43QTKhG102Wy7l5kcjldcCZvDFv9n9pKmBlCx4ZLpQJ9pT3pJGL!1197357898");
		// 使用系统提供的默认的恢复策略
		String s = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?><payout-result type=\"payout\"><item tradeOutNo=\"25461238\" payoutNo=\"123456\"><beginTime>20131115162530</beginTime><endTime></endTime><amt></amt><shouldAmt></shouldAmt><deductionAmt>32</deductionAmt><fee></fee><reqBankSn></reqBankSn><rtnBankCode></rtnBankCode><status></status><returnMsg></returnMsg></item><item tradeOutNo=\"147852369\" payoutNo=\"321654987\"><beginTime></beginTime><endTime></endTime><amt></amt><shouldAmt></shouldAmt><deductionAmt></deductionAmt><fee></fee><reqBankSn></reqBankSn><rtnBankCode></rtnBankCode><status></status><returnMsg></returnMsg></item></payout-result>";
		postMethod.addParameter(new NameValuePair("result", s));
		try {
			// TimeUnit.MILLISECONDS.sleep(sleepTime);
			System.out.println("do");
			int statusCode = httpClient.executeMethod(postMethod);
			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method failed: "
						+ postMethod.getStatusLine());
			}
			System.out.println("do success");
			byte[] responseBody = postMethod.getResponseBody();
			// 处理内容
			System.out.println(new String(responseBody));
		} catch (Throwable e) {
			// 发生网络异常
			e.printStackTrace();
		} finally {
			// 释放连接
			postMethod.releaseConnection();
		}
	}
}
