package com.ryantyer.mahout;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.DataModelBuilder;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.eval.RecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.common.FastByIDMap;
import org.apache.mahout.cf.taste.impl.eval.AverageAbsoluteDifferenceRecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.EuclideanDistanceSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.PreferenceArray;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import java.io.File;
import java.util.List;

/**
 * @author Ryan Tyer
 *         Date: 8/14/11
 */
class RecommenderIntro {
    public static void main(String[] args) throws Exception {
        final DataModel model = new FileDataModel(new File("intro.csv"));

        RecommenderEvaluator evaluator =
                new AverageAbsoluteDifferenceRecommenderEvaluator();


        final UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
        final UserNeighborhood neighborhood =
                new NearestNUserNeighborhood(2, similarity, model);
        RecommenderBuilder builder = new RecommenderBuilder() {
            @Override
            public Recommender buildRecommender(DataModel dataModel) throws TasteException {
                return new GenericUserBasedRecommender(model, neighborhood, similarity);
            }
        };
//
//        DataModelBuilder dataBuilder = new DataModelBuilder() {
//
//
//            @Override
//            public DataModel buildDataModel(FastByIDMap<PreferenceArray> preferenceArrayFastByIDMap) {
//                return null;  //To change body of implemented methods use File | Settings | File Templates.
//            }
//        }

        System.out.println(evaluator.evaluate(builder, null, model, .1, 1));

//        List<RecommendedItem> recommendations = recommender.recommend(1, 3);

//        for (RecommendedItem recommendation : recommendations) {
//            System.out.println(recommendation);
//        }


    }
}
