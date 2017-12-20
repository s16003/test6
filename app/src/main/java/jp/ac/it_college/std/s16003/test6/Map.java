package jp.ac.it_college.std.s16003.test6;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by s16003 on 17/12/14.
 */

public class Map {
    private List<List<Integer>> stage = new ArrayList<>();
    private int flag = 0;
    private int row;
    private int col;
    private InputStream[] is;
    private BufferedReader br;
    private String[] list;

    public Map(InputStream is[]) {
        this.is = is;
    }

    public List<List<Integer>> mapCreate() {
        try {
            try {
                br = new BufferedReader(new InputStreamReader(is[0]));

                String str = br.readLine();
                row = Integer.parseInt(str);
                str = br.readLine();
                col = Integer.parseInt(str);

                for (int i = 0; i < col; i++) {
                    stage.add(new ArrayList<Integer>());
                }
                int count = 0;
                while((str = br.readLine()) != null) {
                    list = str.split(",");

                    for (int j = 0; j < row; j++) {
                        stage.get(count).add(Integer.parseInt(list[j]));
                    }

                    //sub.add(Integer.parseInt(str.split(",")));
                    count++;
                }

            } finally {
                br.close();
            }
        } catch (IOException e) {
        }
        return stage;
    }
}
