package com.thoughtworks.solomonsmines.utils;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class JsonUtilTest {

    @Test
    public void should_return_true_when_given_a_correct_object_json_string() throws Exception {
        String json = "{\"aaa\": 111}";
        boolean result = JsonUtil.isObjectJson(json);
        assertThat(result).isEqualTo(true);
    }

    @Test
    public void should_return_false_when_given_a_correct_array_json_string() throws Exception {
        String json = "[\"aa\", \"bb\"]";
        boolean result = JsonUtil.isObjectJson(json);

        assertThat(result).isEqualTo(false);
    }

    @Test
    public void should_return_false_when_given_a_plain_string() throws Exception {
        String plainString = "abc";
        boolean result = JsonUtil.isObjectJson(plainString);
        assertThat(result).isEqualTo(false);
    }
}