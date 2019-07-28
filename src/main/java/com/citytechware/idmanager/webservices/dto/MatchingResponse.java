package com.citytechware.idmanager.webservices.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
public class MatchingResponse {

    private static final String SUCCESS_MESSAGE = "Success";
    private static final boolean MATCH_FOUND = true;
    private static final String NO_MATCH_FOUND = "No match found";

    private String status;
    private boolean found;
    private int matchesFound;
    List<FingerprintMatch> matches;

    private MatchingResponse() {
        throw new UnsupportedOperationException("Operation not allowed");
    }

    private MatchingResponse(String status, boolean found, int matchesFound, List<FingerprintMatch> matches) {
        this.status = status;
        this.found = found;
        this.matchesFound = matchesFound;
        this.matches = matches;
    }

    public static MatchingResponse createFoundResponse(FingerprintMatch matches) {
        return new MatchingResponse(SUCCESS_MESSAGE, MATCH_FOUND, 1, Collections.singletonList(matches));
    }

    public static MatchingResponse createFoundResponse(List<FingerprintMatch> matches) {
        return new MatchingResponse(SUCCESS_MESSAGE, MATCH_FOUND, matches.size(), matches);
    }

    public static MatchingResponse createNotFoundResponse() {
        return new MatchingResponse(SUCCESS_MESSAGE, !MATCH_FOUND, 0, Collections.emptyList());
    }

}
