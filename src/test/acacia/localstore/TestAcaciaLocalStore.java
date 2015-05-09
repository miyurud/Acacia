package test.acacia.localstore;

import org.acacia.localstore.java.AcaciaHashMapLocalStore;
import org.acacia.localstore.java.AcaciaLocalStore;

public class TestAcaciaLocalStore{
	public static void main(String[] args){
//		AcaciaLocalStore localStore = new AcaciaLocalStore();
//		localStore.run();
		
		AcaciaHashMapLocalStore localStore = new AcaciaHashMapLocalStore(1,480);
		localStore.loadGraph();
		//localStore.addEdge(100l, 101l);
		//localStore.storeGraph();
		System.out.println(localStore.toString());
	}
}