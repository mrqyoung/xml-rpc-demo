package com.mrqyoung.rpc.usesdk;

import java.io.IOException;

import com.mrqyoung.rpc.target.Target;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.XmlRpcRequest;
import org.apache.xmlrpc.server.PropertyHandlerMapping;
import org.apache.xmlrpc.server.RequestProcessorFactoryFactory;
import org.apache.xmlrpc.server.XmlRpcServer;
import org.apache.xmlrpc.webserver.WebServer;

import android.os.Bundle;
import android.app.Activity;

public class MainActivity extends Activity {

	private static final int PORT = 8000;
	private WebServer webServer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		simpleTestOfSdk();
		startRPCServer();

	}

	private void simpleTestOfSdk() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				Target sdk = Target.getInstance();
				System.out.print("12 + 34 = ");
				System.out.println(sdk.add(12, 34));
				System.out.print("78 - 56 = ");
				System.out.println(sdk.subtract(78, 56));
				try {
					System.out.print("http find IP [203.208.48.146] : ");
					System.out.println(sdk.httpGet("203.208.48.146"));
					System.out.print("Device: ");
					System.out.println(sdk.getDeviceInfo());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	/**
	 * 初始化XmlRpcServer，handle被测对象，开启服务
	 */
	private void startRPCServer() {
		webServer = new WebServer(PORT);
		XmlRpcServer xmlRpcServer = webServer.getXmlRpcServer();
		PropertyHandlerMapping phm = new PropertyHandlerMapping();
		/**
		 * 由于SDK中采用了单例模式，外部无法直接初始化，需要通过一个工厂类来完成初始化
		 * 如果SDK中的对象可以直接new，则下面这行以及InstanceFactory类都不需要。
		 */
		phm.setRequestProcessorFactoryFactory(new InstanceFactory());
		phm.setVoidMethodEnabled(true);
		try {
			/**
			 * 为目标SDK添加hander，绑定到"sdk"这个名字上来，
			 * 在client端调用时使用的就是"sdk"名称，后面接Target类里面的方法
			 * 例如Target.add()方法，在外部对应为sdk.add()
			 */
			phm.addHandler("sdk", Target.class);
		} catch (XmlRpcException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		xmlRpcServer.setHandlerMapping(phm);
		try {
			webServer.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 用工厂类来对Target进行初始化
	 * 参见 https://ws.apache.org/xmlrpc/handlerCreation.html
	 */
	private class InstanceFactory implements RequestProcessorFactoryFactory {
		@Override
		public RequestProcessorFactory getRequestProcessorFactory(Class arg0)
				throws XmlRpcException {
			// TODO Auto-generated method stub
			return factory;
		}

		private final Target instance = Target.getInstance();
		private final RequestProcessorFactory factory = new MyRequestProcessorFactory();

		private class MyRequestProcessorFactory implements
				RequestProcessorFactory {
			public Object getRequestProcessor(XmlRpcRequest xmlRpcRequest)
					throws XmlRpcException {
				return instance;
			}
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (webServer != null) {
			webServer.shutdown();
		}
	}

}
