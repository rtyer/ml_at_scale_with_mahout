package com.ryantyer.mahout;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import java.io.File;
import java.io.IOException;

/**
 * @author Ryan Tyer
 *         Date: 8/17/11
 */
public class GroupLensRecommender {


    public static void main(String[] args) throws IOException, TasteException {
        DataModel dataModel = new FileDataModel(new File("ua.base"));

        UserSimilarity similarity = new PearsonCorrelationSimilarity(dataModel);
        UserNeighborhood neighborhood = new NearestNUserNeighborhood(5, similarity, dataModel);

        Recommender reco = new GenericUserBasedRecommender(dataModel, neighborhood, similarity);

        for (RecommendedItem item : reco.recommend(1, 3)) {
            System.out.println(item);
        }
    }
}
