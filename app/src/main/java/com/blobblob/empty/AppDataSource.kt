package com.blobblob.empty

import com.blobblob.empty.appdetails.AppDetails

object AppDataSource {
    fun getApps(): List<AppItem> = listOf(
        AppItem(
            id = "1",
            name = "ВКонтакте",
            description = "Социальная сеть, музыка, видео, звонки и мессенджер в одном приложении.",
            category = AppCategory.SOCIAL,
            iconRes = R.mipmap.ic_launcher
        ),
        AppItem(
            id = "2",
            name = "VK Музыка",
            description = "Миллионы треков, плейлисты, подкасты и персональные рекомендации офлайн.",
            category = AppCategory.ENTERTAINMENT,
            iconRes = R.mipmap.ic_launcher
        ),
        AppItem(
            id = "3",
            name = "VK Мессенджер",
            description = "Быстрые сообщения, групповые чаты, стикеры и видеозвонки без ограничений.",
            category = AppCategory.SOCIAL,
            iconRes = R.mipmap.ic_launcher
        ),
        AppItem(
            id = "4",
            name = "VK Видео",
            description = "Фильмы, сериалы, шоу и видеоролики в высоком качестве с удобным поиском.",
            category = AppCategory.ENTERTAINMENT,
            iconRes = R.mipmap.ic_launcher
        ),
        AppItem(
            id = "5",
            name = "Гильдия Героев: Экшен ММО РПГ",
            description = "Классическая экшен-RPG с рейдами, ареной, магией и прокачкой персонажа.",
            category = AppCategory.GAME,
            iconRes = R.mipmap.ic_launcher
        ),
        AppItem(
            id = "6",
            name = "Почта Mail",
            description = "Быстрый почтовый клиент с поддержкой всех популярных сервисов и защитой от спама.",
            category = AppCategory.PRODUCTIVITY,
            iconRes = R.mipmap.ic_launcher
        ),
        AppItem(
            id = "7",
            name = "Облако Mail",
            description = "Надёжное хранилище фото, видео и документов с автозагрузкой с камеры.",
            category = AppCategory.TOOLS,
            iconRes = R.mipmap.ic_launcher
        ),
        AppItem(
            id = "8",
            name = "Заметки и задачи",
            description = "Организуйте повседневные дела, списки покупок и рабочие проекты легко.",
            category = AppCategory.PRODUCTIVITY,
            iconRes = R.mipmap.ic_launcher
        ),
        AppItem(
            id = "9",
            name = "Пиксельные Рыцари",
            description = "Увлекательный рогалик в ретро-стиле со случайной генерацией уровней.",
            category = AppCategory.GAME,
            iconRes = R.mipmap.ic_launcher
        ),
        AppItem(
            id = "10",
            name = "Файловый менеджер Pro",
            description = "Управление файлами, архивация zip/rar и очистка памяти устройства.",
            category = AppCategory.TOOLS,
            iconRes = R.mipmap.ic_launcher
        )
    )

    fun getShowcaseApps(): List<AppItem> = listOf(
        AppItem(
            id = "sc-1",
            name = "VK Клипы",
            description = "Короткие вертикальные видео на любой вкус и бесконечная лента трендов.",
            category = AppCategory.ENTERTAINMENT,
            iconRes = R.mipmap.ic_launcher
        ),
        AppItem(
            id = "sc-2",
            name = "VK Знакомства",
            description = "Сервис для поиска пары и интересных знакомств по общим интересам.",
            category = AppCategory.SOCIAL,
            iconRes = R.mipmap.ic_launcher
        ),
        AppItem(
            id = "sc-3",
            name = "VK Звонки",
            description = "Видеоконференции высокой чёткости без ограничений по времени звонка.",
            category = AppCategory.TOOLS,
            iconRes = R.mipmap.ic_launcher
        ),
        AppItem(
            id = "sc-4",
            name = "Маруся",
            description = "Голосовой помощник с музыкой, сказками, новостями и умным домом.",
            category = AppCategory.TOOLS,
            iconRes = R.mipmap.ic_launcher
        ),
        AppItem(
            id = "sc-5",
            name = "VK Play Live",
            description = "Стриминговая платформа для любителей видеоигр, киберспорта и трансляций.",
            category = AppCategory.GAME,
            iconRes = R.mipmap.ic_launcher
        ),
        AppItem(
            id = "sc-6",
            name = "Сферум",
            description = "Безопасное учебное пространство для учеников, родителей и учителей.",
            category = AppCategory.PRODUCTIVITY,
            iconRes = R.mipmap.ic_launcher
        ),
        AppItem(
            id = "sc-7",
            name = "Календарь Mail",
            description = "Планирование встреч, событий, напоминания и синхронизация задач.",
            category = AppCategory.PRODUCTIVITY,
            iconRes = R.mipmap.ic_launcher
        ),
        AppItem(
            id = "sc-8",
            name = "Заметки VK",
            description = "Быстрые заметки, чек-листы и списки покупок прямо под рукой.",
            category = AppCategory.PRODUCTIVITY,
            iconRes = R.mipmap.ic_launcher
        ),
        AppItem(
            id = "sc-9",
            name = "RuStore",
            description = "Официальный российский магазин мобильных приложений для Android.",
            category = AppCategory.TOOLS,
            iconRes = R.mipmap.ic_launcher
        )
    )

    fun getAppDetails(appId: String): AppDetails = AppDetails(
        id = appId,
        name = "Гильдия Героев: Экшен ММО РПГ",
        developer = "VK Play",
        category = AppCategory.GAME,
        ageRating = 12,
        size = 223.7f,
        screenshotUrlList = listOf(
            "https://static.rustore.ru/imgproxy/-y8kd-4B6MQ-1OKbAbnoAIMZAzvoMMG9dSiHMpFaTBc/preset:web_scr_lnd_335/plain/https://static.rustore.ru/apk/393868735/content/SCREENSHOT/dfd33017-e90d-4990-aa8c-6f159d546788.jpg@webp",
            "https://static.rustore.ru/imgproxy/dZCvNtRKKFpzOmGlTxLszUPmwi661IhXynYZGsJQvLw/preset:web_scr_lnd_335/plain/https://static.rustore.ru/apk/393868735/content/SCREENSHOT/60ec4cbc-dcf6-4e69-aa6f-cc2da7de1af6.jpg@webp",
            "https://static.rustore.ru/imgproxy/g5whSI1uNqaL2TUO7TFfM8M63vXpWXNCm2vlX4Ahvc4/preset:web_scr_lnd_335/plain/https://static.rustore.ru/apk/393868735/content/SCREENSHOT/c2dde8bc-c4ab-482a-80a5-2789149f598d.jpg@webp",
            "https://static.rustore.ru/imgproxy/TjeurtC7BczOVJt74XhjGYuQnG1l4rx6zpDqyMb00GY/preset:web_scr_lnd_335/plain/https://static.rustore.ru/apk/393868735/content/SCREENSHOT/08318f76-7a9c-43aa-b4a7-1aa878d00861.jpg@webp",
        ),
        iconUrl = "https://static.rustore.ru/imgproxy/APsbtHxkVa4MZ0DXjnIkSwFQ_KVIcqHK9o3gHY6pvOQ/preset:web_app_icon_62/plain/https://static.rustore.ru/apk/393868735/content/ICON/3f605e3e-f5b3-434c-af4d-77bc5f38820e.png@webp",
        description = "Легендарный рейд героев в Фэнтези РПГ. Станьте героем гильдии и сразите мастера подземелья!"
    )
}
