package com.example;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class RecommendationSystem {
    public static void main(String[] args) {
        try {
            // Load dataset
            File file = new File("data.csv");
            DataModel model = new FileDataModel(file);

            // Compute similarity between users using Pearson Correlation
            UserSimilarity similarity = new PearsonCorrelationSimilarity(model);

            // Define user neighborhood (Nearest N Users)
            UserNeighborhood neighborhood = new NearestNUserNeighborhood(2, similarity, model);

            // Build the recommender system
            Recommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);

            // Generate recommendations for a specific user
            int userId = 1; // Example User ID
            int numRecommendations = 3;
            List<RecommendedItem> recommendations = recommender.recommend(userId, numRecommendations);

            // Display recommendations
            System.out.println("Recommendations for User " + userId + ":");
            for (RecommendedItem recommendation : recommendations) {
                System.out.println("Item ID: " + recommendation.getItemID() + ", Estimated Preference: " + recommendation.getValue());
            }

        } catch (IOException | TasteException e) {
            e.printStackTrace();
        }
    }
}
