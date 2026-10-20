package com.blobblob.empty

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
            name = "Гильдия Героев",
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
}
