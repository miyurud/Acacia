package test.acacia.server;

import org.acacia.server.AcaciaInstanceServiceSession;

public class TestAcaciaInstanceServiceSession{
	public static void main(String[] args){
		AcaciaInstanceServiceSession session = new AcaciaInstanceServiceSession();
		session.unzipAndBatchUpload("1", "480");
	}
}

