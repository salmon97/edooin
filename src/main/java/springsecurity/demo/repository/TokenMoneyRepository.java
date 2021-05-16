package springsecurity.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import springsecurity.demo.entity.TokenMoney;

import java.sql.Time;
import java.util.UUID;

@Transactional
public interface TokenMoneyRepository extends JpaRepository<TokenMoney, UUID> {

    @Modifying
    @Query(value = "UPDATE st_join_group st SET balance = st.balance - st.price " +
            "          WHERE st.id IN  (select st.id from  groups g, groups_week_days wk_gr, st_join_group st where " +
            " st.group_id = g.id and g.id = wk_gr.groups_id and wk_gr.week_days_id = (select extract(isodow from current_date)) " +
            " and g.start_hour < :=timeGive and " +
            " st.id not in (select tm.st_join_group_id from token_money tm where tm.date = current_date))" +
            "", nativeQuery = true)
    Integer update_query(Time timeGive);

    @Modifying
    @Query(value = "INSERT INTO token_money(id, date, token_money, st_join_group_id) select uuid_generate_v4(), current_date, st.price ,st.id from  groups g, groups_week_days wk_gr, st_join_group st where " +
            " st.group_id = g.id and g.id = wk_gr.groups_id and wk_gr.week_days_id = (select extract(isodow from current_date)) " +
            " and g.start_hour < :=timeGive  and \n" +
            " st.id not in (select tm.st_join_group_id from token_money tm where tm.date = current_date)", nativeQuery = true)
    Integer insert_query(Time timeGive);

    @Modifying
    @Query(value = "DELETE from token_money where date < DATE(current_date - interval '1 month')", nativeQuery = true)
    Integer deleteOlderOneMonth();
}