package com.sishiancode.springboot.service;

import com.sishiancode.springboot.entities.Profile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Service
public class EditorService extends BaseService {

    private static final int SUGGESTION_SIZE = 5;

    public List<Profile> suggestUser() {
        List<Profile> allProfileList = profileRepository.findAll();

        int size = allProfileList.size();

        if (size >= SUGGESTION_SIZE) {

            ArrayList<Integer> seeds = new ArrayList<Integer>();
            Random randomGenerator = new Random();
            while (seeds.size() < SUGGESTION_SIZE) {

                int random = randomGenerator.nextInt(size);
                if (!seeds.contains(random)) {
                    seeds.add(random);
                }
            }

            List<Profile> suggestProfileList = new ArrayList<>();
            for (int i = 0; i < SUGGESTION_SIZE; i++) {
                suggestProfileList.add(allProfileList.get(seeds.get(i)));
            }

            logger.debug(suggestProfileList.toString());
            return suggestProfileList;

        } else {
            Collections.shuffle(allProfileList);

            logger.debug(allProfileList.toString());
            return allProfileList;
        }

    }


}
