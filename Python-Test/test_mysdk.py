# coding: utf-8


import sys
import unittest

if sys.version_info.major == 3:
    from xmlrpc.client import ServerProxy
else:
    from xmlrpclib import ServerProxy


RPC_SERVER_URI = 'http://192.168.1.4:8000'

class MySdkTests(unittest.TestCase):

    def setUp(self):
        self.sdk = ServerProxy(RPC_SERVER_URI).sdk

    def tearDown(self):
        pass

    def test_add(self, a=123, b=456):
        result = self.sdk.add(a, b)
        print('\nsdk.add(%d, %d) = %d' % (a, b, result))
        self.assertEqual(result, 579)

    def test_subtract(self, a=2015, b=1999):
        result = self.sdk.subtract(a, b)
        print('\nsdk.subtract(%d, %d) = %d' % (a, b, result))
        self.assertEqual(result, 16)

    def test_httpGet(self, ip='203.208.48.146'):
        result = self.sdk.httpGet(ip)
        print('\nsdk.httpGet(%s) is %s' % (ip, result))
        self.assertIsNotNone(result)

    def test_getDeviceInfo(self):
        result = self.sdk.getDeviceInfo()
        print('\nsdk.getDeviceInfo() is:\n%s' % (result))
        self.assertIsNotNone(result)
    


if __name__ == '__main__':
    unittest.main(verbosity=2)
