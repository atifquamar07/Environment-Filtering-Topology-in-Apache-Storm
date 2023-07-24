package org.apache.storm.starter.bolt;

import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class HumidityBolt extends BaseBasicBolt {
    private static final Logger LOG = LoggerFactory.getLogger(HumidityBolt.class);
    Tuple compare = null;

    @Override
    public void execute(Tuple tuple, BasicOutputCollector collector) {
        LOG.debug("Humidity received: " + (tuple.getValueByField("Humidity")));
        if(this.compare == null) {
            collector.emit(new Values(tuple.getValueByField("Humidity")));
            this.compare = tuple;
        }
        else {
            int compareVal = this.compare.getIntegerByField("Humidity");
            int currentVal = tuple.getIntegerByField("Humidity");
            double percentage = ((double)(Math.abs(currentVal-compareVal)*100)/currentVal);
            if(percentage <= 10){
                collector.emit(new Values(tuple.getValues()));
                this.compare=tuple;
            }
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("Humidity"));
    }
}
