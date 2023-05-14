package vn.vietdefi.services.godoo.match;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.exception.ExceptionUtils;
import vn.vietdefi.common.BaseResponse;
import vn.vietdefi.services.godoo.logic.GodooLogic;
import vn.vietdefi.util.log.DebugLogger;
import vn.vietdefi.util.sql.HikariClients;
import vn.vietdefi.util.sql.SQLJavaBridge;

public class GodooMatchService implements IGodooMatchService {
    public JsonObject like(JsonObject data) {
        try {
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            long user1 = data.get("user_id_1").getAsLong();
            long user2 = data.get("user_id_2").getAsLong();
            String relationshipId = GodooLogic.getRelationshipId(user1, user2);
            String query = "SELECT relation,user_id_1,status_user_1,status_user_2 FROM godoo_relationship WHERE id = ?";
            JsonObject relationship = bridge.queryOne(query, relationshipId);
            if (relationship == null) {
                query = "INSERT INTO godoo_relationship(id, user_id_1,user_id_2,relation, status_user_1, status_user_2) VALUE(?,?,?,?,?,?)";
                bridge.queryOne(query, relationshipId, user1, user2, 1, 1, 0);
                return BaseResponse.createFullMessageResponse(0, "success");
            } else {
                int relation = relationship.get("relation").getAsInt();
                long userId1 = relationship.get("user_id_1").getAsLong();
                if (relation == 0) {
                    if (user1 == userId1) {
                        query = "UPDATE godoo_relationship SET relation = ?, status_user_1 = ?, status_user_2 = ?";
                        bridge.queryOne(query, 1, 1, 0);
                        return BaseResponse.createFullMessageResponse(0, "success");
                    } else {
                        query = "UPDATE godoo_relationship SET relation = ?, status_user_1 = ?, status_user_2 = ?";
                        bridge.queryOne(query, 1, 0, 1);
                        return BaseResponse.createFullMessageResponse(0, "success");
                    }
                } else if (relation == 1) {
                    int status1 = relationship.get("status_user_1").getAsInt();
                    if (user1 == userId1) {
                        if (status1 == 1) {
                            return BaseResponse.createFullMessageResponse(10, "liked_before");
                        } else {
                            query = "UPDATE godoo_relationship SET relation = ?, status_user_1 = ?, status_user_2 = ?";
                            bridge.queryOne(query, 2, 1, 1);
                            return BaseResponse.createFullMessageResponse(0, "success");
                        }
                    } else {
                        if (status1 == 0) {
                            return BaseResponse.createFullMessageResponse(10, "liked_before");
                        } else {
                            query = "UPDATE godoo_relationship SET relation = ?, status_user_1 = ?, status_user_2 = ?";
                            bridge.queryOne(query, 2, 1, 1);
                            return BaseResponse.createFullMessageResponse(0, "success");
                        }
                    }
                } else {
                    return BaseResponse.createFullMessageResponse(11, "wrong_method");
                }
            }
        } catch (Exception e) {
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1, "system_error");
        }

    }

    public JsonObject setLove(JsonObject data) {
        try {
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            long user1 = data.get("user_id_1").getAsLong();
            long user2 = data.get("user_id_2").getAsLong();
            String relationshipId = GodooLogic.getRelationshipId(user1, user2);
            String query = "SELECT relation,user_id_1,status_user_1,status_user_2 FROM godoo_relationship WHERE id = ?";
            JsonObject relationship = bridge.queryOne(query, relationshipId);
            int relation = relationship.get("relation").getAsInt();
            long userId1 = relationship.get("user_id_1").getAsLong();
            if (relation == 2) {
                if (user1 == userId1) {
                    query = "UPDATE godoo_relationship SET relation = ?, status_user_1 = ?, status_user_2 = ?";
                    bridge.queryOne(query, 3, 2, 1);
                    return BaseResponse.createFullMessageResponse(0, "success");
                } else {
                    query = "UPDATE godoo_relationship SET relation = ?, status_user_1 = ?, status_user_2 = ?";
                    bridge.queryOne(query, 3, 1, 2);
                    return BaseResponse.createFullMessageResponse(0, "success");
                }
            } else if (relation == 3) {
                int status1 = relationship.get("status_user_1").getAsInt();
                if (user1 == userId1) {
                    if (status1 == 2) {
                        return BaseResponse.createFullMessageResponse(10, "requested_before");
                    } else {
                        query = "UPDATE godoo_relationship SET relation = ?, status_user_1 = ?, status_user_2 = ?";
                        bridge.queryOne(query, 4, 2, 2);
                        return BaseResponse.createFullMessageResponse(0, "success");
                    }
                } else {
                    if (status1 == 1) {
                        return BaseResponse.createFullMessageResponse(10, "requested_before");
                    } else {
                        query = "UPDATE godoo_relationship SET relation = ?, status_user_1 = ?, status_user_2 = ?";
                        bridge.queryOne(query, 4, 2, 2);
                        return BaseResponse.createFullMessageResponse(0, "success");
                    }
                }
            } else {
                return BaseResponse.createFullMessageResponse(11, "wrong_method");
            }

        } catch (Exception e) {
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1, "system_error");
        }
    }
    public JsonObject getYouLiked(long userId) {
        try {
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            String query = "SELECT user_id_2 FROM godoo_relationship WHERE user_id_1 = ? AND  relation = ?";
            JsonArray array = bridge.query(query, userId, 1);
            return BaseResponse.createFullMessageResponse(0, "success", array);
        } catch (Exception e) {
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1, "system_error");
        }
    }

    public JsonObject getLikedYou(long userId) {
        try {
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            String query = "SELECT user_id_1 FROM godoo_relationship WHERE user_id_2 = ? AND  relation = ?";
            JsonArray array = bridge.query(query, userId, 1);
            return BaseResponse.createFullMessageResponse(0, "success", array);
        } catch (Exception e) {
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1, "system_error");
        }
    }

    public JsonObject endMatch(JsonObject data) {
        try {
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            long user1 = data.get("user_id_1").getAsLong();
            long user2 = data.get("user_id_2").getAsLong();
            String relationshipId = GodooLogic.getRelationshipId(user1,user2);
            String query = "UPDATE godoo_relationship SET relation = ?, status_user_1 = ?, status_user_2 = ? WHERE id = ?";
            bridge.query(query, 0, 0, 0,relationshipId);
            return BaseResponse.createFullMessageResponse(0, "success");
        } catch (Exception e) {
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1, "system_error");
        }
    }

    public JsonObject setSchedule(JsonObject data) {
        try {
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            long userId1 = data.get("user_id_1").getAsLong();
            long userId2 = data.get("user_id_2").getAsLong();
            String relationshipId = GodooLogic.getRelationshipId(userId2, userId1);
            String theme = data.get("theme").getAsString();
            String title = data.get("title").getAsString();
            String content = data.get("content").getAsString();
            long time = data.get("time").getAsLong();
            long remindTime = time - 3600 * 1000;
            String address = data.get("address").getAsString();
            int status = data.get("status").getAsInt();
            long createTime  = System.currentTimeMillis();
            String query = "INSERT INTO godoo_schedule(theme,title,content,time,remind_time,address,status,relationshipId,create_time) VALUES(?,?,?,?,?,?,?,?,?) ";
            bridge.queryOne(query, theme, title,content,time,remindTime,address,status,relationshipId,createTime);
            query = "SELECT * FROM godoo_schedule WHERE relationshipId = ? AND create_time = ?";
            data = bridge.queryOne(query,relationshipId, createTime);
            return BaseResponse.createFullMessageResponse(0, "success",data);
        } catch (Exception e) {
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1, "system_error");
        }
    }
    public JsonObject changeSchedule(JsonObject data) {
        try {
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            long scheduleId = data.get("schedule_id").getAsLong();
            String theme = data.get("theme").getAsString();
            String title = data.get("title").getAsString();
            String content = data.get("content").getAsString();
            long time = data.get("time").getAsLong();
            long remindTime = time - 3600 * 1000;
            String address = data.get("address").getAsString();
            int status = data.get("status").getAsInt();
            String query = "UPDATE godoo_schedule SET theme = ?, title = ? ,content = ? ,time = ? ,remind_time = ? ,address = ? ,status = ? WHERE id = ?";
            bridge.queryOne(query, theme, title,content,time,remindTime,address,status,scheduleId);
            return BaseResponse.createFullMessageResponse(0, "success");
        } catch (Exception e) {
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1, "system_error");
        }
    }
    public JsonObject hide(JsonObject data){
        try {
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            long user1 = data.get("user_id_1").getAsLong();
            long user2 = data.get("user_id_2").getAsLong();
            String relationshipId = GodooLogic.getRelationshipId(user1, user2);
            JsonObject relationship = bridge.queryOne("SELECT relation,user_id_1 FROM godoo_relationship WHERE id = ?",relationshipId);
            if(relationship == null){
                bridge.queryOne("INSERT INTO godoo_relationship(id,user_id_1,user_id_2,relation,status_user_1) VALUES(?,?,?,?,?)",relationshipId,user1,user2,-1,-1);
                return BaseResponse.createFullMessageResponse(0, "success");
            }else{
                long userId1 = relationship.get("user_id_1").getAsLong();
                if(user1 == userId1) {
                    bridge.queryOne("UPDATE godoo_relationship SET relation = ? , status_user_1 = ? WHERE id = ?", -1,-1, relationshipId);
                    return BaseResponse.createFullMessageResponse(0, "success");
                }else{
                    bridge.queryOne("UPDATE godoo_relationship SET relation = ? , status_user_2 = ? WHERE id = ?", -1,-1, relationshipId);
                    return BaseResponse.createFullMessageResponse(0, "success");
                }
            }

        } catch (Exception e) {
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1, "system_error");
        }
    }
    public JsonObject report(JsonObject data){
        try {
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            long user1 = data.get("user_id_1").getAsLong();
            long user2 = data.get("user_id_2").getAsLong();
            String relationshipId = GodooLogic.getRelationshipId(user1, user2);
            JsonObject relationship = bridge.queryOne("SELECT relation,user_id_1 FROM godoo_relationship WHERE id = ?",relationshipId);
            if(relationship == null){
                bridge.queryOne("INSERT INTO godoo_relationship(id,user_id_1,user_id_2,relation,status_user_1) VALUES(?,?,?,?,?)",relationshipId,user1,user2,-2,-2);
                return BaseResponse.createFullMessageResponse(0, "success");
            }else{
                long userId1 = relationship.get("user_id_1").getAsLong();
                if(user1 == userId1) {
                    bridge.queryOne("UPDATE godoo_relationship SET relation = ? , status_user_1 = ? WHERE id = ?", -2,-2, relationshipId);
                    return BaseResponse.createFullMessageResponse(0, "success");
                }else{
                    bridge.queryOne("UPDATE godoo_relationship SET relation = ? , status_user_2 = ? WHERE id = ?", -2,-2, relationshipId);
                    return BaseResponse.createFullMessageResponse(0, "success");
                }
            }

        } catch (Exception e) {
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1, "system_error");
        }
    }
}
