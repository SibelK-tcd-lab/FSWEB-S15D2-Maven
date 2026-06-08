package org.example.entity;

import java.util.Set;
import java.util.TreeSet;

public class StringSet {

    public static Set<String> findUniqueWords() {
        String text = "AsCarrollremindedus, anyonewhohasneverlookedintoaganegative's" +
                "eyecan'tbegin toimaginetheunutterabledarkness ofasituation " +
                "whereallthe negativesarefusedinto onesolidpositive. " +
                "Thereisnoradiance, thereisnobrightness, thereis nocolor, " +
                "thereisno light. It'sasifallthe shadesof nightwere concentrated " +
                "into onesolidmass. Ithasthe densityof solidrock, the coldnessof " +
                "ice, andthe silenceof thegrave. Itisunmitigated, unrelieved, " +
                "uncomforted darkness. Ithasno past, nopresent, nofuture. " +
                "Itisasingular, isolated pointin time, a permanentnow. " +
                "Thereisno escapefrom it, thereisno alleviationof it, " +
                "thereisno relief. Itisabsolute, complete, total. " +
                "Ithasno lineof communicationwith theoutside world. " +
                "Itisacell, a dungeon, a grave. Itisawork ofart without " +
                "meaning, a poemwithout words, a songwithout music. " +
                "Itisadeath without resurrection, a nightwithout dawn, " +
                "a winterwithout spring. Itisunmitigated, unrelieved, " +
                "uncomforted darkness.";

        Set<String> uniqueWords = new TreeSet<>();


        String cleanedText = text.replaceAll("[.,'\"\\-!:;?\\(\\)]", "").toLowerCase();

        String[] words = cleanedText.split("\\s+");

        for (String word : words) {
            String trimmed = word.trim();
            if (!trimmed.isEmpty()) {
                uniqueWords.add(trimmed);
            }
        }

        System.out.println("Toplam Benzersiz Kelime Sayısı: " + uniqueWords.size());

        return uniqueWords;
    }
}