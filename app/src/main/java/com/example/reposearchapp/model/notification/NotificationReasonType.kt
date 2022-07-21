package com.example.reposearchapp.model.notification

enum class NotificationReasonType(
    val reasonName: String
) {
    ASSIGN("assign"),
    AUTHOR("author"),
    COMMENT("comment"),
    CI("ci_activity"),
    INVITATION("invitation"),
    MANUAL("manual"),
    MENTION("mention"),
    REVIEW_REQUEST("review_requested"),
    SECURITY("security_alert"),
    STATE_CHANGE("state_change"),
    SUBSCRIBED("subscribed"),
    TEAM_MENTION("team_mentioned"),
    UNKNOWN("unknown");

    companion object {
        fun getReasonType(reason: String?) = values().find { it.reasonName == reason } ?: UNKNOWN
    }
}