package pt.isec.pa.tinypac.model.data;

import java.io.*;
import java.util.*;

/**
 * The Top5 class manages the top 5 scores and provides methods for reading, writing,
 * verifying, and updating the top 5 scores.
 *
 * @author Pedro Nogueira
 * @version 1.0
 * @since 06/2023
 */
public class Top5{

    private final static String folderName = "files/top5/";
    private final static String fileName = "top5.json";
    private List<Top5Data> top5;

    /**
     * Constructs a Top5 object.
     */
    public Top5(){
        top5 = new ArrayList<>();
    }


    /**
     * Reads the top 5 scores from a file.
     * @return True if the reading is successful, false otherwise.
     */
    public Boolean readTop5FromFile(){


        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(new File(folderName + fileName))
        )){
          top5 = (ArrayList<Top5Data>) ois.readObject();
        } catch (Exception e) {
            //e.printStackTrace();
            return false;
        }
        return true;
    }


    /**
     * Writes the top 5 scores into a file.
     * @return True if the writing is successful, false otherwise.
     */
    private Boolean writeTop5IntoFile(){

        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(new File(folderName + fileName))
        )){
            oos.writeObject(top5);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * Verifies if a score is in the top 5.
     * @param value The score value to be verified.
     * @return True if the score is in the top 5, false otherwise.
     */
    public Boolean verifyIfIsInTop5(Integer value){


        readTop5FromFile();

        if(top5.isEmpty() || top5.size() < 5)
            return true;

        for(int i = 0; i < top5.size(); i++){
            if(top5.get(i).getScore() < value)
                return true;
        }
        return false;
    }


    /**
     * Adds a score into the top 5.
     * @param userName The username.
     * @param score The score.
     */
    public void addIntoTop5(String userName, Integer score){


        readTop5FromFile();

        top5.add(new Top5Data(userName, score));
        orderTop5();
        if(top5.size() > 5)
            top5.remove(top5.size() - 1);

        writeTop5IntoFile();
    }


    /**
     * Orders the top 5 scores in descending order.
     */
    public void orderTop5(){
        top5.sort(Comparator.comparing(Top5Data::getScore).reversed());
        if (top5.size() > 5) {
            top5 = top5.subList(0, 5);
        }

    }

    /**
     * Retrieves a copy of the top 5 scores.
     * @return A copy of the top 5 scores.
     */
    public List<Top5Data> getTop5() {
        readTop5FromFile();
        return new ArrayList<>(top5);
    }

    /**
     * Prints the top 5 scores to the console.
     */
    public void printTop5(){
        readTop5FromFile();
        for (Top5Data aux : top5){
            System.out.println(aux.getUserName() + " " +aux.getScore());
        }
    }
}

