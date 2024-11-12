package de.fsiebecke.ShowcaseAppBackend.plugins

enum class SfStageEnum {
    TEST, BETA, PROD
}

enum class MkaStageEnum {
    ENT, INT, ABN
}

enum class AppThemeEnum {
    LIGHT, DARK, SYSTEM
}

enum class UsageFeatureEnum {
    PFM, INCOGNITO, CHARGE_PHONE, PHOTO_TRANSFER, BACKUP, LOGOFF, INDIVIDUALIZE_DASHBOARD, REVIEW
}

enum class AccountSortingEnum {
    ACCOUNTHOLDER, ACCOUNTTYPE
}

enum class LoginModeEnum {
    BIOMETRIC, PASSWORD
}

enum class QuickAccessButtonsEnum {
    NEWINTHEAPP, SENDMONEY, REQUESTMONEY, SEARCHTRANSACTIONS, PFM, APPLEPAY, SINVEST, BRANCHLOCATOR, CARDBLOCKING, MAILBOX, SERVICECENTER
}
