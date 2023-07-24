package org.apache.storm.starter;

import org.apache.storm.Config;
import org.apache.storm.StormSubmitter;
import org.apache.storm.starter.bolt.HumidityBolt;
import org.apache.storm.starter.bolt.PMTenBolt;
import org.apache.storm.starter.bolt.TemperatureBolt;
import org.apache.storm.topology.TopologyBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.storm.starter.spout.EnvSpout;
import org.apache.storm.starter.bolt.PMTwoBolt;

public class EnvTopology {

    private static final Logger LOG = LoggerFactory.getLogger(EnvTopology.class);

    public static void main(String[] args) throws Exception {

        TopologyBuilder builder = new TopologyBuilder();

        builder.setSpout("EnvSpout", new EnvSpout());
        builder.setBolt("PMTwo", new PMTwoBolt(), 1);
        builder.setBolt("PMTen", new PMTenBolt(), 1);
        builder.setBolt("Temperature", new TemperatureBolt(), 1);
        builder.setBolt("Humidity", new HumidityBolt(), 1);

        Config conf = new Config();
        conf.setDebug(false);
        String topoName = "EnvFilter";
        if (args != null && args.length > 0) {
            topoName = args[0];
        }
        conf.setNumWorkers(1);
        StormSubmitter.submitTopologyWithProgressBar(topoName, conf, builder.createTopology());

    }
    
}
