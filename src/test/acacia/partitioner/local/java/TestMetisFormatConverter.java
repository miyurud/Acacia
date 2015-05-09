package test.acacia.partitioner.local.java;

import org.acacia.partitioner.local.java.MetisPartitioner;

public class TestMetisFormatConverter{
	public static void main(String[] args){
		MetisPartitioner converter = new MetisPartitioner();
		//converter.convert("/home/miyurud/Acacia/graphs/simple_graph.dl");
		//converter.convert("/home/miyurud/Acacia/graphs/simple_graph-no-header.dl");
		///home/miyurud/Acacia/graphs/powergrid.dl
		boolean isDistrbutedCentralPartitions = false;
		//converter.convert("graphname", "/home/miyurud/Acacia/graphs/powergrid.dl", "/home/miyurud/tmp", 4, isDistrbutedCentralPartitions);
		//converter.printGraphContent();
	}
}
