package db.migration;

import org.flywaydb.core.api.migration.spring.SpringJdbcMigration;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

/**
 * Created by zhangbin on 2018/7/24.
 */
public class V3__update implements SpringJdbcMigration {
    public void migrate(JdbcTemplate jdbcTemplate) throws Exception {
        //得到选课信息
        List rows = jdbcTemplate.queryForList("select * from select_info");
        //将class_info增加一个字段，记录选择该课程的学生id
        jdbcTemplate.execute("ALTER TABLE `class_info` ADD `selected_stu_id` VARCHAR(100)  NULL  DEFAULT ''  AFTER `name`;");
        //将select_info表删除
        jdbcTemplate.execute("DROP TABLE `select_info`");
//        int a = 1;
//        if ( a == 1) {
//            throw new RuntimeException("抛异常了");
//        }
        for (int i=0;i<rows.size();i++){
            Map map = (Map)rows.get(i);
            int stuId = (Integer) map.get("stu_id");
            int classId = (Integer) map.get("class_id");
            String stuList = jdbcTemplate.queryForObject("select selected_stu_id from class_info where id = ?",new Object[]{classId},java.lang.String.class);
            StringBuilder sb = new StringBuilder(stuList);
            if (sb.toString().equals("")){
                sb.append(stuId);
            }else {
                sb.append(","+stuId);
            }
            jdbcTemplate.update("UPDATE `class_info` SET selected_stu_id = ? where id = ?",new Object[]{sb.toString(),classId});
        }
    }
}
