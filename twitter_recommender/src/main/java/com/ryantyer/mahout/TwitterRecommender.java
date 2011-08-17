package com.ryantyer.mahout;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.GenericBooleanPrefDataModel;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericBooleanPrefUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.CachingUserSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.TanimotoCoefficientSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import java.io.File;
import java.io.IOException;

/**
 * @author Ryan Tyer
 *         Date: 8/13/11
 */
public class TwitterRecommender {
    public static void main(String[] args) throws IOException, TasteException {
        GenericBooleanPrefDataModel model = new GenericBooleanPrefDataModel(GenericBooleanPrefDataModel.toDataMap(new FileDataModel(new File("rtyer.csv"))));

        UserSimilarity similarity = new CachingUserSimilarity(new TanimotoCoefficientSimilarity(model), model);
        UserNeighborhood neighborhood = new NearestNUserNeighborhood(5, similarity, model);
        Recommender reco = new GenericBooleanPrefUserBasedRecommender(model, neighborhood, similarity);

        System.out.println(reco.recommend(14342388, 3));
    }
}
