package com.nintex.nintexflights.helpers

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class FlightSearchUtilTest {

    @Test
    fun `Do not let user search for flights without entering origin code`() {
        var result = FlightSearchUtil.isSearchValid("", "CMB", "2021-10-10T00:00:00.000+11:00", "2021-10-10T00:00:00.000+11:00")
        assertThat(result).isFalse()
    }

    @Test
    fun `Do not let user search for flights without entering destination code`() {
        var result = FlightSearchUtil.isSearchValid("MLB", "", "2021-10-10T00:00:00.000+11:00", "2021-10-10T00:00:00.000+11:00")
        assertThat(result).isFalse()
    }

    @Test
    fun `Do not let user search for flights without entering departure date`() {
        var result = FlightSearchUtil.isSearchValid("MLB", "CMB", "", "2021-10-10T00:00:00.000+11:00")
        assertThat(result).isFalse()
    }

    @Test
    fun `Do not let user search for flights without entering return date`() {
        var result = FlightSearchUtil.isSearchValid("MLB", "CMB", "2021-10-10T00:00:00.000+11:00", "")
        assertThat(result).isFalse()
    }

    @Test
    fun `Do not let user search for flights if origin code is the same as destination code`() {
        var result = FlightSearchUtil.isSearchValid("MLB", "MLB", "2021-10-10T00:00:00.000+11:00", "2021-10-10T00:00:00.000+11:00")
        assertThat(result).isFalse()
    }

    @Test
    fun `Do not let user search for flights if departure date is the same as return date`() {
        var result = FlightSearchUtil.isSearchValid("MLB", "CMB", "2021-10-10T00:00:00.000+11:00", "2021-10-10T00:00:00.000+11:00")
        assertThat(result).isFalse()
    }

    @Test
    fun `Do not let the user to search if the return date is before departure date`() {
        var result = FlightSearchUtil.isSearchValid("MLB", "CMB", "2021-10-10T00:00:00.000+11:00", "2021-10-2T00:00:00.000+11:00")
        assertThat(result).isFalse()
    }

    @Test
    fun `Allow the user to search if the return date is after departure date`() {
        var result = FlightSearchUtil.isSearchValid("MLB", "CMB", "2021-10-10T00:00:00.000+11:00", "2021-10-20T00:00:00.000+11:00")
        assertThat(result).isTrue()
    }

    @Test
    fun `Do not enable the search button if the origin code is not entered`() {
        var result = FlightSearchUtil.shouldEnableSearchButton("", "CMB", "2021-10-10T00:00:00.000+11:00", "2021-10-20T00:00:00.000+11:00")
        assertThat(result).isFalse()
    }

    @Test
    fun `Do not enable the search button if the destination code is not entered`() {
        var result = FlightSearchUtil.shouldEnableSearchButton("MLB", "", "2021-10-10T00:00:00.000+11:00", "2021-10-20T00:00:00.000+11:00")
        assertThat(result).isFalse()
    }

    @Test
    fun `Do not enable the search button if the departure date is not entered`() {
        var result = FlightSearchUtil.shouldEnableSearchButton("MLB", "CMB", "", "2021-10-20T00:00:00.000+11:00")
        assertThat(result).isFalse()
    }

    @Test
    fun `Do not enable the search button if the return date is not entered`() {
        var result = FlightSearchUtil.shouldEnableSearchButton("MLB", "CMB", "2021-10-20T00:00:00.000+11:00", "")
        assertThat(result).isFalse()
    }

    @Test
    fun `Enable the search button if all the parameters are provided`() {
        var result = FlightSearchUtil.shouldEnableSearchButton("MLB", "CMB", "2021-10-20T00:00:00.000+11:00", "2021-10-20T00:00:00.000+11:00")
        assertThat(result).isTrue()
    }

    @Test
    fun `Allow the user to search if all the parameters are provided`() {
        var result = FlightSearchUtil.isSearchValid("MLB", "CMB", "2021-10-10T00:00:00.000+11:00", "2021-10-20T00:00:00.000+11:00")
        assertThat(result).isTrue()
    }

}