package com.mrqyoung.rpc.testmysdk;


import java.net.URL;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.junit.After;
import org.junit.Before;

public class TestMySDK extends TestCase {
	
	private static final String RPC_SERVER_URI = "http://192.168.1.4:8000/";
	private static XmlRpcClient rpc;

	@Before
	public void setUp() throws Exception {
		XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
		config.setServerURL(new URL(RPC_SERVER_URI));
		rpc = new XmlRpcClient();
		rpc.setConfig(config);
	}

	@After
	public void tearDown() throws Exception {
	}
	
	public void testAdd() throws XmlRpcException {
		Object[] params = new Object[]{123, 456};
		int result = (Integer) rpc.execute("sdk.add", params);
		System.out.println("sdk.add(123, 456) = " + result);
		Assert.assertEquals(579, result);
	}
	
	public void testSubtract() throws XmlRpcException {
		Object[] params = new Object[]{2015, 1999};
		int result = (Integer) rpc.execute("sdk.subtract", params);
		System.out.println("sdk.subtract(2015, 1999) = " + result);
		Assert.assertEquals(16, result);
	}
	
	public void testHttpGet() throws XmlRpcException {
		Object[] params = new Object[]{"203.208.48.146"};
		String result = (String) rpc.execute("sdk.httpGet", params);
		System.out.println("sdk.httpGet(203.208.48.146) is " + result);
		Assert.assertNotNull(result);
	}
	
	public void testGetDeviceInfo() throws XmlRpcException {
		String result = (String) rpc.execute("sdk.getDeviceInfo", new Object[]{});
		System.out.println("sdk.getDeviceInfo() is:\n" + result);
		Assert.assertNotNull(result);
	}

}
