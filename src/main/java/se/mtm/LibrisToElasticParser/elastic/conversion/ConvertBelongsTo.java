package se.mtm.LibrisToElasticParser.elastic.conversion;

import se.mtm.LibrisToElasticParser.libris.model.Graph;
import se.mtm.LibrisToElasticParser.libris.model.SeriesMembership;
import se.mtm.LibrisToElasticParser.elastic.model.opds.BelongsTo;
import se.mtm.LibrisToElasticParser.elastic.model.opds.Series;

public class ConvertBelongsTo {

    static BelongsTo getBelongsto(Graph item) {
        BelongsTo belongsTo = new BelongsTo();
        Series series = new Series();
        series.name = getSeriesName(item);
        belongsTo.series = series;

        return belongsTo;
    }

    static String getSeriesName(Graph item) {
        if (item.seriesMembership != null) {
            for (SeriesMembership s : item.seriesMembership) {
                if (s != null && s.type != null && s.inSeries != null) {
                    if (s.type.equals("SeriesMembership") && s.seriesStatement != null) {
                        return s.seriesStatement.get(0).toString();
                    }
                    if (s.type.equals("SeriesMembership") && s.inSeries.instanceOf.hasTitle != null) {
                        return s.inSeries.instanceOf.hasTitle.get(0).mainTitle;
                    }
                }
            }
        }
        return null;
    }
}
