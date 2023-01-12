package com.ktk.domain.entity;

public enum VoteType {
	UP(1), DOWN(-1),
    ;

    VoteType(int direction) {
    }
}
