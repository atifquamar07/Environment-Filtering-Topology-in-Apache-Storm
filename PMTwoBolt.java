package org.apache.storm.starter.bolt;

import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class PMTwoBolt extends BaseBasicBolt {
    private static final Logger LOG = LoggerFactory.getLogger(PMTwoBolt.class);
    Tuple compare = null;

    @Override
    public void execute(Tuple tuple, BasicOutputCollector collector) {
        LOG.debug("PM2 received: " + (tuple.getValueByField("PMTwo")));
        if(this.compare == null) {
            collector.emit(new Values(tuple.getValueByField("PMTwo")));
            this.compare = tuple;
        }
        else {
            int compareVal = this.compare.getIntegerByField("PMTwo");
            int currentVal = tuple.getIntegerByField("PMTwo");
            double percentage = ((double)(Math.abs(currentVal-compareVal)*100)/currentVal);
            if(percentage <= 10){
                collector.emit(new Values(tuple.getValues()));
                this.compare=tuple;
            }
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("PM2"));
    }
}
