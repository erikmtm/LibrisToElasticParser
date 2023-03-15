/*
 * Copyright (c) 2023. Myndigheten för tillgängliga medier (MTM)
 */
package se.mtm.LibrisToElasticParser.elastic.conversion;

import se.mtm.LibrisToElasticParser.elastic.model.opds.OpdsItem;

import java.util.List;

/**
 * Holds the opds lists of nota/elasticbooks specific items
 */
public class OpdsDataSet {

    public final List<OpdsItem> opds;
    public final List<OpdsItem> opdsSingles;

    public OpdsDataSet(List<OpdsItem> opds, List<OpdsItem> opdsSingles) {
        this.opds = opds;
        this.opdsSingles = opdsSingles;
    }
}
