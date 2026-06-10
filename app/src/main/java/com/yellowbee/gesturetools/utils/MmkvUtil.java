package com.yellowbee.gesturetools.utils;

import com.tencent.mmkv.MMKV;

/**
 * @author huangfeng
 * @date 2024/5/7
 * @description
 * @since
 */
public class MmkvUtil {
    private static final String TAG = "MmkvUtil";
    private static final String FACE_THRESHOLD = "faceThreshold";
    private static final String LIVENESS_THRESHOLD = "livenessThreshold";
    private static final String FACE_COMPARE_TIME = "faceCompareTime";
    private static final String OPEN_DOOR_TIME = "openDoorTime";
    private static final String CAMERA_DEGREES = "cameraDegrees";
    private static final String CAMERA_ID = "cameraId";
    private static final String IDCARD_LIMIT_SWITCH = "idcard_limit_switch";
    private static final String IDCARD_LIMIT_TIME = "idcard_limit_time";
    private static final String LOCAL_RECORD_SAVE_DAYS = "local_record_save_days";
    private static final String MACHINE_ID = "MACHINE_ID";
    private static final String UPLOAD_INTERVAL = "upload_interval";

    private static final String PARAM_UPDATE_TIME = "param_update_time";

    private static final String PARAM_CONFIG = "turnstile_param_config";

    private static final String PERSON_RESUME_INDEX = "person_resume_index";
    private static final String PERSON_RESUME_TOTAL = "person_resume_total";




    /**
     * 对比阈值
     * @return
     */
    @Deprecated
    public static int getFaceThreshold() {
        return MMKV.defaultMMKV().getInt(FACE_THRESHOLD, 60);
    }

    /**
     * 对比阈值
     * @param faceThreshold
     */
    @Deprecated
    public static void setFaceThreshold(int faceThreshold) {
        MMKV.defaultMMKV().putInt(FACE_THRESHOLD, faceThreshold);
    }

    /**
     * 活体阈值
     * @return
     */
    @Deprecated
    public static int getLivenessThreshold() {
        return MMKV.defaultMMKV().getInt(LIVENESS_THRESHOLD, 60);
    }

    /**
     * 活体阈值
     */
    @Deprecated
    public static void setLivenessThreshold(int livenessThreshold) {
        MMKV.defaultMMKV().putInt(LIVENESS_THRESHOLD, livenessThreshold);;
    }

    /**
     * 核验时间
     */
    @Deprecated
    public static int getFaceCompareTime() {
        return MMKV.defaultMMKV().getInt(FACE_COMPARE_TIME, 15);
    }

    /**
     * 核验时间
     */
    @Deprecated
    public static void setFaceCompareTime(int faceCompareTime) {
        MMKV.defaultMMKV().putInt(FACE_COMPARE_TIME, faceCompareTime);
    }

    /**
     * 开门时间
     */
    @Deprecated
    public static int getOpenDoorTime() {
        return MMKV.defaultMMKV().getInt(OPEN_DOOR_TIME, 10);
    }

    /**
     * 开门时间
     */
    @Deprecated
    public static void setOpenDoorTime(int openDoorTime) {
        MMKV.defaultMMKV().putInt(OPEN_DOOR_TIME, openDoorTime);
    }

    /**
     * 摄像头角度
     */
    @Deprecated
    public static int getCameraDegrees() {
        return MMKV.defaultMMKV().getInt(CAMERA_DEGREES, 270);
    }

    /**
     * 摄像头角度
     */
    @Deprecated
    public static void setCameraDegrees(int cameraDegrees) {
        MMKV.defaultMMKV().putInt(CAMERA_DEGREES, cameraDegrees);
    }

    /**
     * 摄像头id
     */
    @Deprecated
    public static int getCameraId() {
        return MMKV.defaultMMKV().getInt(CAMERA_ID, 0);
    }

    /**
     * 摄像头id
     */
    @Deprecated
    public static void setCameraId(int cameraId) {
        MMKV.defaultMMKV().putInt(CAMERA_ID, cameraId);
    }

    /**
     * 刷证限制时间是否开启
     */
    @Deprecated
    public static boolean isLimitIdcard(){
        return MMKV.defaultMMKV().getBoolean(IDCARD_LIMIT_SWITCH, false);
    }

    /**
     * 刷证限制时间是否开启
     */
    @Deprecated
    public static void setLimitIdcard(boolean limit){
        MMKV.defaultMMKV().putBoolean(IDCARD_LIMIT_SWITCH, limit);
    }

    /**
     * 刷证限制时间
     */
    @Deprecated
    public static String getLimitIdcardTime(){
        return MMKV.defaultMMKV().getString(IDCARD_LIMIT_TIME, "08:00-20:00");
    }

    /**
     * 刷证限制时间
     */
    @Deprecated
    public static void setLimitIdcardTime(String time){
        MMKV.defaultMMKV().putString(IDCARD_LIMIT_TIME, time);
    }

    /**
     * 本地记录保存天数
     */
    @Deprecated
    public static int getLocalRecordSaveDays(){
        return MMKV.defaultMMKV().getInt(LOCAL_RECORD_SAVE_DAYS, 7);
    }

    /**
     * 本地记录保存天数
     */
    @Deprecated
    public static void setLocalRecordSaveDays(int days){
        MMKV.defaultMMKV().putInt(LOCAL_RECORD_SAVE_DAYS, days);
    }

    /**
     * 机器码
     */
    @Deprecated
    public static String getMachineId(){
        return MMKV.defaultMMKV().getString(MACHINE_ID, "");
    }

    /**
     * 机器码
     */
    @Deprecated
    public static void setMachineId(String machineId){
        MMKV.defaultMMKV().putString(MACHINE_ID, machineId);
    }

    /**
     * 上传时间间隔
     */
    @Deprecated
    public static int getUploadInterval(){
        return MMKV.defaultMMKV().getInt(UPLOAD_INTERVAL, 15);
    }

    /**
     * 本地记录保存天数
     */
    @Deprecated
    public static void setUploadInterval(int minutes){
        MMKV.defaultMMKV().putInt(UPLOAD_INTERVAL, minutes);
    }

    /**
     * 参数更新时间
     */
    @Deprecated
    public static void setUpdateTime(String time){
        MMKV.defaultMMKV().putString(PARAM_UPDATE_TIME, time);
    }

    /**
     * 参数更新时间
     */
    @Deprecated
    public static String getUpdateTime(){
        return MMKV.defaultMMKV().getString(PARAM_UPDATE_TIME, "1997-01-01 08:00:00");
    }

    public static void setPersonResumeIndex(int pageIndex){
        MMKV.defaultMMKV().putInt(PERSON_RESUME_INDEX, pageIndex);
    }

    public static int getPersonResumeIndex(){
        return MMKV.defaultMMKV().getInt(PERSON_RESUME_INDEX, -1);
    }

    public static void setPersonResumeTotal(int totalPage){
        MMKV.defaultMMKV().putInt(PERSON_RESUME_TOTAL, totalPage);
    }

    public static int getPersonResumeTotal(){
        return MMKV.defaultMMKV().getInt(PERSON_RESUME_TOTAL, -1);
    }

}
