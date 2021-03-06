package com.github.k0kubun.github_ranking.repository.dao;

import com.github.k0kubun.github_ranking.model.UserRank;

import java.util.List;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlBatch;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.BatchChunkSize;

public interface UserRankDao
{
    @SqlUpdate("delete from user_ranks where stargazers_count between :min and :max")
    long deleteStarsBetween(@Bind("min") int min, @Bind("max") int max);

    // Lower rank is larger number
    @SqlUpdate("delete from user_ranks where rank between :highest and :lowest")
    long deleteRankBetween(@Bind("highest") int highest, @Bind("lowest") int lowest);

    @SqlBatch("insert into user_ranks (stargazers_count, rank, created_at, updated_at) " +
            "values (:stargazersCount, :rank, current_timestamp(), current_timestamp())")
    @BatchChunkSize(5000)
    void bulkInsert(@BindBean List<UserRank> userRanks);
}
