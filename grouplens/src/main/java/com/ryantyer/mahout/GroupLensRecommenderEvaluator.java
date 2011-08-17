package com.ryantyer.mahout;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.eval.RecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.eval.AverageAbsoluteDifferenceRecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.CachingUserSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.EuclideanDistanceSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import java.io.File;
import java.io.IOException;

/**
 * @author Ryan Tyer
 *         Date: 8/17/11
 */
public class GroupLensRecommenderEvaluator {
    public static void main(String args[]) throws IOException, TasteException {
        DataModel model = new FileDataModel(new File("ua.base"));


        RecommenderEvaluator evaluator = new AverageAbsoluteDifferenceRecommenderEvaluator();
        RecommenderBuilder recommenderBuilder = new RecommenderBuilder() {
            @Override
            public Recommender buildRecommender(DataModel model)
                    throws TasteException {
                UserSimilarity similarity = new CachingUserSimilarity(new EuclideanDistanceSimilarity(model), model);
                UserNeighborhood neighborhood = new NearestNUserNeighborhood(5, similarity, model);
                return new GenericUserBasedRecommender(model, neighborhood, similarity);
            }


        };
        double score = evaluator.evaluate(recommenderBuilder, null, model, .7, 1.0);

        System.out.println(score);

    }

}
