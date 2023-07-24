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

public class TemperatureBolt extends BaseBasicBolt {
    private static final Logger LOG = LoggerFactory.getLogger(TemperatureBolt.class);
    Tuple compare=null;

    @Override
    public void execute(Tuple tuple, BasicOutputCollector collector) {
        LOG.debug("Temperature received: " + (tuple.getValueByField("Temperature")));
        if(this.compare == null) {
            collector.emit(new Values(tuple.getValueByField("Temperature")));
            this.compare = tuple;
        }
        else {
            int compareVal = this.compare.getIntegerByField("Temperature");
            int currentVal = tuple.getIntegerByField("Temperature");
            double percentage = ((double)(Math.abs(currentVal-compareVal)*100)/currentVal);
            if(percentage <= 5){
                collector.emit(new Values(tuple.getValues()));
                this.compare=tuple;
            }
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("Temperature"));
    }
}
