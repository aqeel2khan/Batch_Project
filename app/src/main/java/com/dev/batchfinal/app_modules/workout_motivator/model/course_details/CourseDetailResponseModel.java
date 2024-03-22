
package com.dev.batchfinal.app_modules.workout_motivator.model.course_details;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class CourseDetailResponseModel {

    @SerializedName("data")
    private Data mData;
    @SerializedName("error")
    private Error mError;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("status")
    private Boolean mStatus;

    public Data getData() {
        return mData;
    }

    public void setData(Data data) {
        mData = data;
    }

    public Error getError() {
        return mError;
    }

    public void setError(Error error) {
        mError = error;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public Boolean getStatus() {
        return mStatus;
    }

    public void setStatus(Boolean status) {
        mStatus = status;
    }

    public class Data {

        @SerializedName("coach_detail")
        private CoachDetail mCoachDetail;
        @SerializedName("course_id")
        private Long mCourseId;
        @SerializedName("course_image")
        private String mCourseImage;
        @SerializedName("course_level")
        private CourseLevel mCourseLevel;
        @SerializedName("course_name")
        private String mCourseName;
        @SerializedName("course_price")
        private String mCoursePrice;
        @SerializedName("course_promo_video")
        private Object mCoursePromoVideo;
        @SerializedName("course_repetition")
        private String mCourseRepetition;
        @SerializedName("course_validity")
        private String mCourseValidity;
        @SerializedName("created_at")
        private String mCreatedAt;
        @SerializedName("description")
        private String mDescription;
        @SerializedName("discount_price")
        private Object mDiscountPrice;
        @SerializedName("duration")
        private String mDuration;
        @SerializedName("goals")
        private List<Goal> mGoals;
        @SerializedName("per_day_workout")
        private String mPerDayWorkout;
        @SerializedName("status")
        private Long mStatus;
        @SerializedName("updated_at")
        private String mUpdatedAt;
        @SerializedName("user_id")
        private Long mUserId;
        @SerializedName("weight_required")
        private String mWeightRequired;
        @SerializedName("workout_type")
        private List<WorkoutType> mWorkoutType;
        @SerializedName("workouts")
        private List<Workout> mWorkouts;

        public CoachDetail getCoachDetail() {
            return mCoachDetail;
        }

        public void setCoachDetail(CoachDetail coachDetail) {
            mCoachDetail = coachDetail;
        }

        public Long getCourseId() {
            return mCourseId;
        }

        public void setCourseId(Long courseId) {
            mCourseId = courseId;
        }

        public String getCourseImage() {
            return mCourseImage;
        }

        public void setCourseImage(String courseImage) {
            mCourseImage = courseImage;
        }

        public CourseLevel getCourseLevel() {
            return mCourseLevel;
        }

        public void setCourseLevel(CourseLevel courseLevel) {
            mCourseLevel = courseLevel;
        }

        public String getCourseName() {
            return mCourseName;
        }

        public void setCourseName(String courseName) {
            mCourseName = courseName;
        }

        public String getCoursePrice() {
            return mCoursePrice;
        }

        public void setCoursePrice(String coursePrice) {
            mCoursePrice = coursePrice;
        }

        public Object getCoursePromoVideo() {
            return mCoursePromoVideo;
        }

        public void setCoursePromoVideo(Object coursePromoVideo) {
            mCoursePromoVideo = coursePromoVideo;
        }

        public String getCourseRepetition() {
            return mCourseRepetition;
        }

        public void setCourseRepetition(String courseRepetition) {
            mCourseRepetition = courseRepetition;
        }

        public String getCourseValidity() {
            return mCourseValidity;
        }

        public void setCourseValidity(String courseValidity) {
            mCourseValidity = courseValidity;
        }

        public String getCreatedAt() {
            return mCreatedAt;
        }

        public void setCreatedAt(String createdAt) {
            mCreatedAt = createdAt;
        }

        public String getDescription() {
            return mDescription;
        }

        public void setDescription(String description) {
            mDescription = description;
        }

        public Object getDiscountPrice() {
            return mDiscountPrice;
        }

        public void setDiscountPrice(Object discountPrice) {
            mDiscountPrice = discountPrice;
        }

        public String getDuration() {
            return mDuration;
        }

        public void setDuration(String duration) {
            mDuration = duration;
        }

        public List<Goal> getGoals() {
            return mGoals;
        }

        public void setGoals(List<Goal> goals) {
            mGoals = goals;
        }

        public String getPerDayWorkout() {
            return mPerDayWorkout;
        }

        public void setPerDayWorkout(String perDayWorkout) {
            mPerDayWorkout = perDayWorkout;
        }

        public Long getStatus() {
            return mStatus;
        }

        public void setStatus(Long status) {
            mStatus = status;
        }

        public String getUpdatedAt() {
            return mUpdatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            mUpdatedAt = updatedAt;
        }

        public Long getUserId() {
            return mUserId;
        }

        public void setUserId(Long userId) {
            mUserId = userId;
        }

        public String getWeightRequired() {
            return mWeightRequired;
        }

        public void setWeightRequired(String weightRequired) {
            mWeightRequired = weightRequired;
        }

        public List<WorkoutType> getWorkoutType() {
            return mWorkoutType;
        }

        public void setWorkoutType(List<WorkoutType> workoutType) {
            mWorkoutType = workoutType;
        }

        public List<Workout> getWorkouts() {
            return mWorkouts;
        }

        public void setWorkouts(List<Workout> workouts) {
            mWorkouts = workouts;
        }

    }
    public class CoachDetail {

        @SerializedName("id")
        private Long mId;
        @SerializedName("name")
        private String mName;
        @SerializedName("profile_bio")
        private Object mProfileBio;
        @SerializedName("profile_photo_path")
        private String mProfilePhotoPath;

        public Long getId() {
            return mId;
        }

        public void setId(Long id) {
            mId = id;
        }

        public String getName() {
            return mName;
        }

        public void setName(String name) {
            mName = name;
        }

        public Object getProfileBio() {
            return mProfileBio;
        }

        public void setProfileBio(Object profileBio) {
            mProfileBio = profileBio;
        }

        public String getProfilePhotoPath() {
            return mProfilePhotoPath;
        }

        public void setProfilePhotoPath(String profilePhotoPath) {
            mProfilePhotoPath = profilePhotoPath;
        }

    }

    public class CourseLevel {

        @SerializedName("id")
        private Long mId;
        @SerializedName("level_name")
        private String mLevelName;
        @SerializedName("status")
        private Long mStatus;

        public Long getId() {
            return mId;
        }

        public void setId(Long id) {
            mId = id;
        }

        public String getLevelName() {
            return mLevelName;
        }

        public void setLevelName(String levelName) {
            mLevelName = levelName;
        }

        public Long getStatus() {
            return mStatus;
        }

        public void setStatus(Long status) {
            mStatus = status;
        }

    }

    public class Goal {

        @SerializedName("batchgoal")
        private List<Batchgoal> mBatchgoal;
        @SerializedName("course_id")
        private Long mCourseId;
        @SerializedName("goal_id")
        private Long mGoalId;
        @SerializedName("id")
        private Long mId;

        public List<Batchgoal> getBatchgoal() {
            return mBatchgoal;
        }

        public void setBatchgoal(List<Batchgoal> batchgoal) {
            mBatchgoal = batchgoal;
        }

        public Long getCourseId() {
            return mCourseId;
        }

        public void setCourseId(Long courseId) {
            mCourseId = courseId;
        }

        public Long getGoalId() {
            return mGoalId;
        }

        public void setGoalId(Long goalId) {
            mGoalId = goalId;
        }

        public Long getId() {
            return mId;
        }

        public void setId(Long id) {
            mId = id;
        }

    }

    public class WorkoutType {

        @SerializedName("course_id")
        private Long mCourseId;
        @SerializedName("id")
        private Long mId;
        @SerializedName("workout_type_id")
        private Long mWorkoutTypeId;
        @SerializedName("workoutdetail")
        private Workoutdetail mWorkoutdetail;

        public Long getCourseId() {
            return mCourseId;
        }

        public void setCourseId(Long courseId) {
            mCourseId = courseId;
        }

        public Long getId() {
            return mId;
        }

        public void setId(Long id) {
            mId = id;
        }

        public Long getWorkoutTypeId() {
            return mWorkoutTypeId;
        }

        public void setWorkoutTypeId(Long workoutTypeId) {
            mWorkoutTypeId = workoutTypeId;
        }

        public Workoutdetail getWorkoutdetail() {
            return mWorkoutdetail;
        }

        public void setWorkoutdetail(Workoutdetail workoutdetail) {
            mWorkoutdetail = workoutdetail;
        }

    }

    public class Workout {

        @SerializedName("calorie_burn")
        private String mCalorieBurn;
        @SerializedName("course_duration_id")
        private Long mCourseDurationId;
        @SerializedName("course_id")
        private Long mCourseId;
        @SerializedName("created_at")
        private String mCreatedAt;
        @SerializedName("day_name")
        private String mDayName;
        @SerializedName("description")
        private String mDescription;
        @SerializedName("no_of_exercise")
        private Long mNoOfExercise;
        @SerializedName("Row")
        private Long mRow;
        @SerializedName("status")
        private Long mStatus;
        @SerializedName("updated_at")
        private String mUpdatedAt;
        @SerializedName("workout_time")
        private String mWorkoutTime;

        public String getCalorieBurn() {
            return mCalorieBurn;
        }

        public void setCalorieBurn(String calorieBurn) {
            mCalorieBurn = calorieBurn;
        }

        public Long getCourseDurationId() {
            return mCourseDurationId;
        }

        public void setCourseDurationId(Long courseDurationId) {
            mCourseDurationId = courseDurationId;
        }

        public Long getCourseId() {
            return mCourseId;
        }

        public void setCourseId(Long courseId) {
            mCourseId = courseId;
        }

        public String getCreatedAt() {
            return mCreatedAt;
        }

        public void setCreatedAt(String createdAt) {
            mCreatedAt = createdAt;
        }

        public String getDayName() {
            return mDayName;
        }

        public void setDayName(String dayName) {
            mDayName = dayName;
        }

        public String getDescription() {
            return mDescription;
        }

        public void setDescription(String description) {
            mDescription = description;
        }

        public Long getNoOfExercise() {
            return mNoOfExercise;
        }

        public void setNoOfExercise(Long noOfExercise) {
            mNoOfExercise = noOfExercise;
        }

        public Long getRow() {
            return mRow;
        }

        public void setRow(Long row) {
            mRow = row;
        }

        public Long getStatus() {
            return mStatus;
        }

        public void setStatus(Long status) {
            mStatus = status;
        }

        public String getUpdatedAt() {
            return mUpdatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            mUpdatedAt = updatedAt;
        }

        public String getWorkoutTime() {
            return mWorkoutTime;
        }

        public void setWorkoutTime(String workoutTime) {
            mWorkoutTime = workoutTime;
        }

    }
    public class Batchgoal {

        @SerializedName("goal_name")
        private String mGoalName;
        @SerializedName("id")
        private Long mId;
        @SerializedName("status")
        private Long mStatus;

        public String getGoalName() {
            return mGoalName;
        }

        public void setGoalName(String goalName) {
            mGoalName = goalName;
        }

        public Long getId() {
            return mId;
        }

        public void setId(Long id) {
            mId = id;
        }

        public Long getStatus() {
            return mStatus;
        }

        public void setStatus(Long status) {
            mStatus = status;
        }

    }

    public class Workoutdetail {

        @SerializedName("id")
        private Long mId;
        @SerializedName("workout_type")
        private String mWorkoutType;

        public Long getId() {
            return mId;
        }

        public void setId(Long id) {
            mId = id;
        }

        public String getWorkoutType() {
            return mWorkoutType;
        }

        public void setWorkoutType(String workoutType) {
            mWorkoutType = workoutType;
        }

    }

    public class Error {


    }

}
