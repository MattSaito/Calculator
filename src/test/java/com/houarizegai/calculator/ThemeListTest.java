package com.houarizegai.calculator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import java.util.*;
import static org.assertj.core.api.Assertions.*;

import com.houarizegai.calculator.theme.properties.ThemeList;
import com.houarizegai.calculator.theme.properties.Theme;




class ThemeListTest {

    private ThemeList themeList;
    private Theme theme1;
    private Theme theme2;

    @BeforeEach
    void setUp() {
        theme1 = new Theme();
        theme1.setName("Dark");
        theme2 = new Theme();
        theme2.setName("Light");
        themeList = new ThemeList();
        themeList.setThemes(new ArrayList<>(Arrays.asList(theme1, theme2)));
    }

    @Test
    void testGetThemesExposesInternalList() {
        List<Theme> themesRef = themeList.getThemes();
        themesRef.clear(); // Mutate the returned list

        // The internal list should now be empty, proving exposure
        assertThat(themeList.getThemes()).isEmpty();
    }

    @Test
    void testSetThemes() {
        List<Theme> newThemes = new ArrayList<>(Collections.singletonList(theme1));
        themeList.setThemes(newThemes);
        assertThat(themeList.getThemes()).containsExactly(theme1);
    }

    @Test
    void testGetThemesAsMap() {
        Map<String, Theme> map = themeList.getThemesAsMap();
        assertThat(map)
            .hasSize(2)
            .containsEntry("Dark", theme1)
            .containsEntry("Light", theme2);
    }
}