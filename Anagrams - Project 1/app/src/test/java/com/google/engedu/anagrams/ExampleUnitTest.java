package com.google.engedu.anagrams;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */

@RunWith(MockitoJUnitRunner.class)
public class ExampleUnitTest {

    @Mock
    private AnagramDictionary dictionary;

    @Test
    public void getAnagram() {

        assertThat(dictionary.sortLetters("abcdefgh"), equalTo("abcdedfgh"));
    }
}