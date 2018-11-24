<?xml version="1.0" encoding="UTF-8"?>
<rss version="2.0" xmlns:dc="http://purl.org/dc/elements/1.1/"  >
  <channel>
    <title>Хабр / Интересные публикации</title>
    <link>https://habr.com/</link>
    <description><![CDATA[Интересные публикации на Хабре]]></description>
    <language>ru</language>
    <managingEditor>editor@habr.com</managingEditor>
    <generator>habr.com</generator>
    <pubDate>Sat, 24 Nov 2018 10:20:15 GMT</pubDate>
    <lastBuildDate></lastBuildDate>
      <image>
        <link>https://habr.com/</link>
        <url>https://habr.com/images/logo.png</url>
        <title>Хабр</title>
      </image>

          

    <item>
      <title><![CDATA[[Из песочницы] Установка BigBlueButton на Ubuntu 16.04]]></title>
      <guid isPermaLink="true">https://habr.com/post/430870/</guid>
      <link>https://habr.com/post/430870/?utm_source=habrahabr&amp;utm_medium=rss&amp;utm_campaign=430870</link>
      <description><![CDATA[<p>Доброго времени суток сегодня я решился написать в конце то концов свой первый пост.</p><br/>
<p>Много о BigBlueButton, далее BBB, рассказывать не буду, скажу лишь о том что BBB это:</p><br/>
<ul>
<li>Бесплатно (GNU Lesser General Public License).</li>
<li>Для Видео-Аудио Конференций.</li>
<li>Для Презентаций.</li>
<li>Для Вебинаров.</li>
</ul> <a href="https://habr.com/post/430870/?utm_source=habrahabr&amp;utm_medium=rss&amp;utm_campaign=430870#habracut">Читать дальше &rarr;</a>]]></description>
      
      <pubDate>Fri, 23 Nov 2018 13:06:45 GMT</pubDate>
      <dc:creator>naeternitas</dc:creator>
      <category><![CDATA[Системное администрирование]]></category><category><![CDATA[Софт]]></category>
      <category>bigbluebutton</category><category>Ubuntu 16.04</category>
    </item>





      <item>
      <title><![CDATA[[recovery mode] Выводим лгуна на чистую воду: собеседование – это не трудовые отношения. Естественно]]></title>
      <guid isPermaLink="true">https://habr.com/post/430912/</guid>
      <link>https://habr.com/post/430912/?utm_source=habrahabr&amp;utm_medium=rss&amp;utm_campaign=430912</link>
      <description><![CDATA[Высказывать свое мнение — это окей. Спорить выдирая фразы из контекста, перворячивая смысл на 180 градусов, придумывать то, чего нет – не окей. Подавать свое спорное мнение от имени гос органа и третьих лиц (юристов) – совсем не окей.<br>
<br>
Эта краткая заметка – ответ на публикацию <a href="https://habr.com/post/427929/">«Минтруд: тестовое задание — это трудовые отношения»</a>. Меня задела безаппеляционность автора, его стремление обмануть людей, неспособность читать законы и тексты так, как они написаны. Поэтому аналогично автору я послал расширенный вопрос в Минтруд и вот получил ответ. То, о чем мы все догадывались, подтвердилось. <br> <a href="https://habr.com/post/430912/?utm_source=habrahabr&amp;utm_medium=rss&amp;utm_campaign=430912#habracut">Читать дальше →</a>]]></description>
      
      <pubDate>Sat, 24 Nov 2018 08:05:15 GMT</pubDate>
      <dc:creator>le1ic</dc:creator>
      <category><![CDATA[Карьера в IT-индустрии]]></category><category><![CDATA[Управление персоналом]]></category>
      <category><![CDATA[найм персонала]]></category><category><![CDATA[трудовой кодекс]]></category><category><![CDATA[права человека]]></category>
    </item>







      <item>
      <title><![CDATA[Модуль управления силовым преобразователем: разработка и сборка]]></title>
      <guid isPermaLink="true">https://habr.com/post/430908/</guid>
      <link>https://habr.com/post/430908/?utm_source=habrahabr&amp;utm_medium=rss&amp;utm_campaign=430908</link>
      <description><![CDATA[Не для кого не секрет, что сложные современные преобразователи, например, online UPS, работают под управлением DSP/МК или ASIC. Основными поставщиками DSP для силовой электроники являются две компании — Texas Instruments и Infineon, но сегодня речь пойдет о продукции компании STMicroelectronics — серии STM32F334. Данная линейка МК предназначена для управления электроприводом и построения силовых преобразователей: PFC, инверторов, импульсных блоков питания, UPS и прочих. <br>
<br>
Конечно, серия F334 не может противостоять «мощи» таких популярных решений как TMS320F28335 и прочим, но у нее есть одно важное преимущество — стоимость. Старший камень STM32F334R8T6 стоит 5$, имеет на борту необходимый набор периферии (HRPWM, ADC, компараторы) и производительность для построения достаточно мощных преобразователей (десятки кВт) с хорошей надежностью и устойчивостью к отказу.<br>
<br>
Для разработчика электроники важна экосистема вокруг того DSP/МК с которым он работает: документация, отладочные средства, примеры кода и железа. У TI все это имеется, да — дорого, да — сложно купить, но есть и именно поэтому в большинстве современных решений в области электропривода и энергетики стоят TMS320. Компания ST же почему-то обошла вниманием серию F334, хотя документация хорошего качество как и на любой STM32 имеется, а вот примеры железа с полноценным кодом и отладочные платы отсутствуют (игрушка F3348-Disco не считается). Что же — будем исправлять этот недостаток.<br>
<br>
В своей <a href="https://habr.com/post/428550/">прошлой статье</a> я рассказал о своем проекте «комплекта разработчика» и даже продемонстрировал один из компонентов — силовой модуль. Сегодня я расскажу о 2-м (всего их будет 3) модуле, который позволяет реализовать любую топологию преобразователя и при этом стоит в разы дешевле конкурентов. Проект разумеется открыт и все исходники можно посмотреть в конце данной статьи. <br>
<br>
<a href="https://habrastorage.org/webt/9g/c7/oi/9gc7oiynuaziphnssxpsk-5qu88.jpeg"><img src="https://habrastorage.org/webt/9g/c7/oi/9gc7oiynuaziphnssxpsk-5qu88.jpeg"></a><br> <a href="https://habr.com/post/430908/?utm_source=habrahabr&amp;utm_medium=rss&amp;utm_campaign=430908#habracut">Читать дальше →</a>]]></description>
      
      <pubDate>Sat, 24 Nov 2018 08:44:57 GMT</pubDate>
      <dc:creator>NordicEnergy</dc:creator>
      <category><![CDATA[DIY или Сделай сам]]></category><category><![CDATA[Программирование микроконтроллеров]]></category><category><![CDATA[Производство и разработка электроники]]></category><category><![CDATA[Схемотехника]]></category><category><![CDATA[Электроника для начинающих]]></category>
      <category><![CDATA[stm32]]></category><category><![CDATA[силовая электроника]]></category><category><![CDATA[motor control]]></category><category><![CDATA[dsp]]></category><category><![CDATA[рэа]]></category>
    </item>







  



    <item>
      <title><![CDATA[[Перевод] Добавляем глубину 2D-спрайтам с помощью отрисованных вручную карт нормалей]]></title>
      <guid isPermaLink="true">https://habr.com/post/430754/</guid>
      <link>https://habr.com/post/430754/?utm_source=habrahabr&amp;utm_medium=rss&amp;utm_campaign=430754</link>
      <description><![CDATA[<div style="text-align:center;"><img src="https://habrastorage.org/getpro/habr/post_images/e29/a6e/6e6/e29a6e6e60640ac36c79981ff998b1f5.jpg"></div><br>
Пиксель-арт вечен, но это не значит, что инструменты и методики работы с ним должны оставаться на уровне 1993 года.<br>
<br>
Британский разработчик игр Cardboard Sword работает над двухмерным стелс-платформером <a href="https://siegeandsandfox.com/"><em>The Siege and the Sandfox</em></a>, создаваемом в Unreal Engine 4 с Paper2D. Команда публикует серию <a href="https://community.playstarbound.com/forums/sf-devdiaries/">дневников разработки</a>, знакомящую других разработчиков с тем, как использовать эти инструменты в собственных играх.<br>
<br>
Одна из основных технических особенностей <em>The Siege and the Sandfox</em> — использование отрисованных вручную тайлсетов и спрайтов с картами нормалей, позволяющих применять в этой игре с достаточно традиционным двумерным пиксель-артом способы трёхмерного освещения.<br>
<br>
Один из арт-директоров Cardboard Sword Кейт Дюк-Кокс недавно рассказал Gamasutra, как компания пришла к использованию этой техники, о том, почему она подходит игре и как можно применить подобные техники в собственных 2D-играх.<br> <a href="https://habr.com/post/430754/?utm_source=habrahabr&amp;utm_medium=rss&amp;utm_campaign=430754#habracut">Читать дальше →</a>]]></description>
      
      <pubDate>Sat, 24 Nov 2018 06:40:53 GMT</pubDate>
      <dc:creator>PatientZero</dc:creator>
      <category><![CDATA[Дизайн игр]]></category><category><![CDATA[Работа с 3D-графикой]]></category><category><![CDATA[Разработка игр]]></category>
      <category>normal mapping</category><category>карты нормалей</category><category>пиксель-арт</category><category>двухмерная игра</category><category>освещение</category>
    </item>



      <item>
      <title><![CDATA[Информационная безопасность интернета вещей: кто вещь, а кто хозяин?]]></title>
      <guid isPermaLink="true">https://habr.com/post/430822/</guid>
      <link>https://habr.com/post/430822/?utm_source=habrahabr&amp;utm_medium=rss&amp;utm_campaign=430822</link>
      <description><![CDATA[<img src="https://habrastorage.org/webt/4b/1y/ve/4b1yvenag1ag-unmzimjk82s_xm.png"><br>
<a href="https://www.enisa.europa.eu/publications/good-practices-for-security-of-iot?fbclid=IwAR1q-chv88kZRsIESHtGTEwbA0Mbx8mb9hV1Euqy-Y--IHVYvLuFhGuvi6o">Источник</a><br>
<br>
Ни для кого не секрет, что в области интернета вещей (Internet of Things, IoT), пожалуй, меньше всего порядка в плане обеспечения информационной безопасности (ИБ). Сегодня <a href="https://habr.com/post/427035/">мы наблюдаем</a> развивающуюся технологию, постоянно меняющийся ландшафт отрасли, прогнозы, порой уводящие в сторону от реальности, десятки организаций, пытающихся объявить себя законодателями в той или иной области, хотя бы «на час». Актуальность проблемы подчеркивается эпическими инцидентами. Industroyer, BrickerBot, Mirai – и это лишь видимая верхушка айсберга, а что «день грядущий нам готовит»? Если продолжать двигаться по течению, то хозяевами интернета вещей станут ботнеты и прочие «вредоносы». А вещи с непродуманным функционалом будут довлеть над теми, кто попытается стать их хозяином.<br>
<br>
В ноябре 2018 <a href="https://www.enisa.europa.eu/">ENISA</a> (The European Union Agency for Network and Information Security) выпустило документ <a href="https://www.enisa.europa.eu/publications/good-practices-for-security-of-iot?fbclid=IwAR1q-chv88kZRsIESHtGTEwbA0Mbx8mb9hV1Euqy-Y--IHVYvLuFhGuvi6o">«Good Practices for Security of Internet of Things in the context of Smart Manufacturing»</a>, в котором собраны всевозможные практики обеспечения кибербезопасности для промышленного интернета вещей, причем проанализировано около сотни документов с лучшими практиками в этой области. Что же находится «под капотом» этой попытки объять необъятное? В статье выполнен обзор содержания.<br> <a href="https://habr.com/post/430822/?utm_source=habrahabr&amp;utm_medium=rss&amp;utm_campaign=430822#habracut">Читать дальше →</a>]]></description>
      
      <pubDate>Fri, 23 Nov 2018 13:18:42 GMT</pubDate>
      <dc:creator>Vladimir_Sklyar</dc:creator>
      <category><![CDATA[IT-стандарты]]></category><category><![CDATA[Интернет вещей]]></category><category><![CDATA[Информационная безопасность]]></category><category><![CDATA[Промышленное программирование]]></category>
      <category><![CDATA[iot]]></category><category><![CDATA[iiot]]></category><category><![CDATA[стандарты безопасности]]></category><category><![CDATA[информационная безопасность]]></category><category><![CDATA[ENISA]]></category><category><![CDATA[интернет вещей]]></category><category><![CDATA[промышленный интернет вещей]]></category>
    </item>







      <item>
      <title><![CDATA[Ночью спит спокойно мама — мы собираем OpenCV для Raspbian'a]]></title>
      <guid isPermaLink="true">https://habr.com/post/430906/</guid>
      <link>https://habr.com/post/430906/?utm_source=habrahabr&amp;utm_medium=rss&amp;utm_campaign=430906</link>
      <description><![CDATA[<p>Последние пару недель были непростыми для нашей команды. Выпускали <a href="https://github.com/opencv">OpenCV 4</a>, а вместе с ним готовились к <a href="https://software.intel.com/openvino-toolkit">Intel's OpenVINO toolkit</a> R4, в состав которого входит OpenCV. Думаешь, отвлекусь на время, посмотрю, как обычно, форумы про OpenCV, да комментарии пользователей, и тут на тебе, модно стало говорить что OpenCV не IoT, что под Raspberry Pi собрать — припоя не хватает, что на ночь <code>make -j2</code> ставить — утром будет готово, если повезёт.</p><br>
<p>Поэтому предлагаю дружно взяться за руки и посмотреть, как же можно собирать библиотеку OpenCV для 32-битной операционной системы, исполняемой на ARM процессоре, используя ресурсы машины с 64-битной OS, движимой отличной архитектурой CPU. <del>Колдовство</del> Кросс-компиляция, не иначе!</p> <a href="https://habr.com/post/430906/?utm_source=habrahabr&amp;utm_medium=rss&amp;utm_campaign=430906#habracut">Читать дальше →</a>]]></description>
      
      <pubDate>Fri, 23 Nov 2018 22:28:08 GMT</pubDate>
      <dc:creator>dkurt</dc:creator>
      <category><![CDATA[DIY или Сделай сам]]></category><category><![CDATA[GitHub]]></category><category><![CDATA[Open source]]></category><category><![CDATA[Python]]></category><category><![CDATA[Разработка на Raspberry Pi]]></category>
      <category><![CDATA[opencv]]></category><category><![CDATA[raspberry pi]]></category><category><![CDATA[deep learning]]></category><category><![CDATA[computer vision]]></category>
    </item>







  



    <item>
      <title><![CDATA[[Перевод] Аккумуляторные батареи от Tesla/Panasonic — самые доступные на рынке аккумуляторных батарей для электромобилей]]></title>
      <guid isPermaLink="true">https://habr.com/post/430804/</guid>
      <link>https://habr.com/post/430804/?utm_source=habrahabr&amp;utm_medium=rss&amp;utm_campaign=430804</link>
      <description><![CDATA[<img src="https://habrastorage.org/webt/qy/n_/m3/qyn_m3vl50cuyseygpssk1i0ccc.jpeg"><br>
<br>
Согласно данным аналитика UBS Колина Лангана (Colin Langan), разборка литий-ионных аккумуляторных батарей от Panasonic/Tesla, LG Chem, Samsung SDI и Contemporary Amperex Technology (CATL) показала, что батареи производства Panasonic/Tesla примерно на 20% дешевле, чем следующее лучшее предложение от LG Chem.<br> <a href="https://habr.com/post/430804/?utm_source=habrahabr&amp;utm_medium=rss&amp;utm_campaign=430804#habracut">Читать дальше →</a>]]></description>
      
      <pubDate>Thu, 22 Nov 2018 19:30:32 GMT</pubDate>
      <dc:creator>striver</dc:creator>
      <category><![CDATA[Транспорт будущего]]></category><category><![CDATA[Энергия и элементы питания]]></category>
      <category>аккумуляторные батареи</category><category>тесла</category><category>панасоник</category>
    </item>



  

    <item>
      <title><![CDATA[[Из песочницы] Комбинация кроссплатформенного и нативного подхода в разработке мобильных приложений]]></title>
      <guid isPermaLink="true">https://habr.com/post/430892/</guid>
      <link>https://habr.com/post/430892/?utm_source=habrahabr&amp;utm_medium=rss&amp;utm_campaign=430892</link>
      <description><![CDATA[Выпускать приложения для лишь одной мобильной платформы – не актуально и нужно заботиться о разработке сразу двух версий, для iOS и Android. И здесь можно выбрать два пути: работать на «нативных» языках программирования для каждой операционной системы или использовать кроссплатформенные фреймворки.<br/>
<br/>
При разработке одного из проектов в компании DD Planet я сделал ставку на последний вариант. И в этой статье расскажу об опыте разработки кроссплатформенного приложения, проблемах, с которыми мы столкнулись, и найденных решениях.<br/>
 <a href="https://habr.com/post/430892/?utm_source=habrahabr&amp;utm_medium=rss&amp;utm_campaign=430892#habracut">Читать дальше &rarr;</a>]]></description>
      
      <pubDate>Fri, 23 Nov 2018 15:26:38 GMT</pubDate>
      <dc:creator>DiegoV</dc:creator>
      <category><![CDATA[.NET]]></category><category><![CDATA[Xamarin]]></category><category><![CDATA[Программирование]]></category><category><![CDATA[Разработка мобильных приложений]]></category>
      <category>разработка мобильных приложений</category><category>разработка под ios</category><category>разработка под android</category><category>xamarin</category>
    </item>





      <item>
      <title><![CDATA[Детерминированные исключения и обработка ошибок в «C++ будущего»]]></title>
      <guid isPermaLink="true">https://habr.com/post/430690/</guid>
      <link>https://habr.com/post/430690/?utm_source=habrahabr&amp;utm_medium=rss&amp;utm_campaign=430690</link>
      <description><![CDATA[<div class="oembed"><div><div style="left: 0; width: 100%; height: 0; position: relative; padding-bottom: 56.2493%;"><iframe src="https://www.youtube.com/embed/cbUTAoHy6Ls?rel=0&amp;showinfo=1&amp;hl=en-US" style="border: 0; top: 0; left: 0; width: 100%; height: 100%; position: absolute;" allowfullscreen scrolling="no"></iframe></div></div></div><br>
<p>Странно, что на Хабре до сих пор не было упомянуто о наделавшем шуму предложении к стандарту C++ под названием "Zero-overhead deterministic exceptions". Исправляю это досадное упущение.</p><br>
<p>Если вас беспокоит оверхед исключений, или вам приходилось компилировать код без поддержки исключений, или просто интересно, что будет с обработкой ошибок в C++2b (отсылка к <a href="https://habr.com/post/426965/">недавнему посту</a>), прошу под кат. Вас ждёт выжимка из всего, что сейчас можно найти по теме, и пара опросов.</p> <a href="https://habr.com/post/430690/?utm_source=habrahabr&amp;utm_medium=rss&amp;utm_campaign=430690#habracut">Читать дальше →</a>]]></description>
      
      <pubDate>Fri, 23 Nov 2018 15:33:13 GMT</pubDate>
      <dc:creator>Anton3</dc:creator>
      <category><![CDATA[C++]]></category><category><![CDATA[Программирование]]></category>
      <category><![CDATA[c++]]></category><category><![CDATA[c++20]]></category><category><![CDATA[c++23]]></category><category><![CDATA[c++26]]></category><category><![CDATA[expected]]></category><category><![CDATA[optional]]></category><category><![CDATA[статические исключения]]></category><category><![CDATA[throws]]></category><category><![CDATA[contracts. контракты]]></category><category><![CDATA[destruction]]></category><category><![CDATA[destructive move]]></category><category><![CDATA[trivially relocatable]]></category><category><![CDATA[error_code]]></category><category><![CDATA[error code]]></category><category><![CDATA[коды ошибок]]></category><category><![CDATA[proposal]]></category><category><![CDATA[status_code]]></category><category><![CDATA[herb sutter]]></category><category><![CDATA[герб саттер]]></category><category><![CDATA[niall douglas]]></category><category><![CDATA[найл дуглас]]></category><category><![CDATA[std::error]]></category><category><![CDATA[error handling]]></category><category><![CDATA[exception handling]]></category>
    </item>







      <item>
      <title><![CDATA[Первый лазер в истории: каким он был]]></title>
      <guid isPermaLink="true">https://habr.com/post/430900/</guid>
      <link>https://habr.com/post/430900/?utm_source=habrahabr&amp;utm_medium=rss&amp;utm_campaign=430900</link>
      <description><![CDATA[Как известно, лазер – это устройство способное к усилению света путем вынужденного излучения. И возможность построения этого устройства была сначала предсказана в теории, а лишь много лет спустя удалось построить первый образец. Напомню, что вынужденное излучение было объяснено с точки зрения квантовой теории Эйнштейном, а первое воплощение этого принципа в железе началось в 50х годах ХХ века независимо различными группами ученых, наиболее известными из которых стали Ч. Таунс, А. М. Прохоров и Н. Г. Басов. Тогда им удалось построить первый квантовый генератор – мазер, который генерировал излучение в области сантиметровых волн. Непокоренным на то время оставался оптический диапазон, и о том, как его удалось покорить я и постараюсь рассказать в этой статье. <br>
<br>
<img src="https://habrastorage.org/webt/xs/rp/12/xsrp123zaz9tkrtsrgc96422sy4.jpeg" alt="image"><br>
<br> <a href="https://habr.com/post/430900/?utm_source=habrahabr&amp;utm_medium=rss&amp;utm_campaign=430900#habracut">Читать дальше →</a>]]></description>
      
      <pubDate>Fri, 23 Nov 2018 17:48:34 GMT</pubDate>
      <dc:creator>Laserbuilder</dc:creator>
      <category><![CDATA[DIY или Сделай сам]]></category><category><![CDATA[Лазеры]]></category>
      <category><![CDATA[лазеры]]></category><category><![CDATA[рубин]]></category><category><![CDATA[импульсная лампа]]></category><category><![CDATA[спиральная лампа]]></category><category><![CDATA[ИФК-2000]]></category><category><![CDATA[ИФП-5000]]></category><category><![CDATA[высоковольтные конденсаторы]]></category>
    </item>







  



    <item>
      <title><![CDATA[[Перевод] Где Agile ужасен, особенно Scrum]]></title>
      <guid isPermaLink="true">https://habr.com/post/430890/</guid>
      <link>https://habr.com/post/430890/?utm_source=habrahabr&amp;utm_medium=rss&amp;utm_campaign=430890</link>
      <description><![CDATA[<i>Гибкость</i> — без сомнения хорошая вещь, и в <a href="http://www.agilemanifesto.org/">манифесте Agile</a> есть смысл. По сравнению с хрупкой практикой под названием «водопад», Agile заметно лучше. Тем не менее, на практике гибкие подходы часто наносят глубокий вред, и в действительности вряд ли здесь уместна дихотомия Agile/Waterfall.<br>
<br>
Я видел, как множество вариантов Agile, называемых Scrum, реально убивают компанию. Под «убивают» я имею в виду не «ухудшение культуры», а скорее когда акции компании падают почти на 90% за два года.<br>
<br>
<h1>Что такое Agile?</h1><br>
Agile вырос из среды веб-консалтинга, где он приносил определённую пользу: при работе с привередливыми клиентами, которые не знают, чего они хотят, обычно приходится выбирать из двух вариантов. Или одолеть клиента: установить ожидания, соответствующую оплату за переделки и поддерживать отношения равенства, а не подчинения. Или принять некорректное поведение клиента (как, скажем, приходится многим дизайнерам) и ориентировать рабочий поток вокруг клиентской дисфункции.<br> <a href="https://habr.com/post/430890/?utm_source=habrahabr&amp;utm_medium=rss&amp;utm_campaign=430890#habracut">Читать дальше →</a>]]></description>
      
      <pubDate>Fri, 23 Nov 2018 15:56:28 GMT</pubDate>
      <dc:creator>m1rko</dc:creator>
      <category><![CDATA[Agile]]></category><category><![CDATA[Управление персоналом]]></category><category><![CDATA[Управление продуктом]]></category><category><![CDATA[Управление проектами]]></category><category><![CDATA[Управление разработкой]]></category>
      <category>Agile</category><category>Scrum</category><category>гибкая разработка</category>
    </item>



      <item>
      <title><![CDATA[Спички это не игрушка?]]></title>
      <guid isPermaLink="true">https://habr.com/post/430864/</guid>
      <link>https://habr.com/post/430864/?utm_source=habrahabr&amp;utm_medium=rss&amp;utm_campaign=430864</link>
      <description><![CDATA[<i>(Пара сувениров эпохи мини-ЭВМ)</i><br>
<br>
<img src="https://habrastorage.org/webt/0e/t3/pe/0et3peo3udbvgqmjgrav5sr616s.jpeg" alt="image"> <i>(мини-ЭВМ PDP-11 (<a href="https://ru.wikipedia.org/wiki/%D0%A4%D0%B0%D0%B9%D0%BB:Pdp-11-40.jpg">источник</a>) и спичечный калькулятор)</i><br>
<br>
В числе прочих исторических событий начало 1990-х ознаменовалось неспешным закатом эпохи мини-ЭВМ. От этого времени у меня осталось два сувенира. <br> <a href="https://habr.com/post/430864/?utm_source=habrahabr&amp;utm_medium=rss&amp;utm_campaign=430864#habracut">Читать дальше →</a>]]></description>
      
      <pubDate>Fri, 23 Nov 2018 12:28:26 GMT</pubDate>
      <dc:creator>third112</dc:creator>
      <category><![CDATA[Гаджеты]]></category><category><![CDATA[История IT]]></category><category><![CDATA[Компьютерное железо]]></category><category><![CDATA[Носимая электроника]]></category><category><![CDATA[Старое железо]]></category>
      <category><![CDATA[мини-компьютер]]></category><category><![CDATA[DEC]]></category><category><![CDATA[pdp-11]]></category><category><![CDATA[vax]]></category><category><![CDATA[старое компьютерное железо]]></category><category><![CDATA[история it]]></category><category><![CDATA[калькулятор]]></category><category><![CDATA[дискеты]]></category>
    </item>







      <item>
      <title><![CDATA[Создание игры «Like Coins» на Godot Engine. Часть 2]]></title>
      <guid isPermaLink="true">https://habr.com/post/430800/</guid>
      <link>https://habr.com/post/430800/?utm_source=habrahabr&amp;utm_medium=rss&amp;utm_campaign=430800</link>
      <description><![CDATA[<p>Я надеюсь вы заждались второй части статьи затрагивающей аспекты разработки игр при помощи "Godot Engine", на примере игры "Like Coins"? На повестке дня приготовлено много всего "вкусного" и "полезного". Сразу оговорюсь, что в этой статье мы завершим ранее начатую игру, начало которой вы можете прочесть <a href="https://habr.com/post/429234/">здесь</a>, но цикл статей продолжится, т.к. материала оказалось на столько много, что заставило меня часть его отложить в сторону, но мы обязательно вернемся к нему позже. Да начнется "gamedev"!</p> <a href="https://habr.com/post/430800/?utm_source=habrahabr&amp;utm_medium=rss&amp;utm_campaign=430800#habracut">Читать дальше →</a>]]></description>
      
      <pubDate>Fri, 23 Nov 2018 08:40:18 GMT</pubDate>
      <dc:creator>jimmyjonezz</dc:creator>
      <category><![CDATA[Программирование]]></category><category><![CDATA[Разработка игр]]></category>
      <category><![CDATA[godot]]></category><category><![CDATA[godot 3.1]]></category><category><![CDATA[godotengine]]></category>
    </item>







      <item>
      <title><![CDATA[Обновление библиотеки SPPermission и хейтер]]></title>
      <guid isPermaLink="true">https://habr.com/post/430886/</guid>
      <link>https://habr.com/post/430886/?utm_source=habrahabr&amp;utm_medium=rss&amp;utm_campaign=430886</link>
      <description><![CDATA[— “<i>Какая-то библиотека, кто-то обновил… Хейтер тут причем? Почему мне, отличному разработчику, нужно тратить время на эту статью?</i>”<br>
<br>
мог подумать ты, мой дорогой друг. Не нужно тратить время. Это просто вечернее чтиво с долей несмешных юморесок. Текст будет о библиотеке <a href="https://github.com/IvanVorobei/SPPermission">RequestPermission</a> и о её загадочном авторе.<br> <a href="https://habr.com/post/430886/?utm_source=habrahabr&amp;utm_medium=rss&amp;utm_campaign=430886#habracut">Читать дальше →</a>]]></description>
      
      <pubDate>Fri, 23 Nov 2018 14:32:47 GMT</pubDate>
      <dc:creator>IvanVorobei</dc:creator>
      <category><![CDATA[Objective C]]></category><category><![CDATA[Swift]]></category><category><![CDATA[Xcode]]></category><category><![CDATA[Разработка под iOS]]></category>
      <category><![CDATA[ios]]></category><category><![CDATA[Xcode]]></category><category><![CDATA[RequestPermission]]></category><category><![CDATA[GitHub]]></category><category><![CDATA[SPPermission]]></category><category><![CDATA[swift]]></category><category><![CDATA[cocoapods]]></category>
    </item>







      <item>
      <title><![CDATA[Кто победит в дебатах mesh-сети против ESB]]></title>
      <guid isPermaLink="true">https://habr.com/post/430872/</guid>
      <link>https://habr.com/post/430872/?utm_source=habrahabr&amp;utm_medium=rss&amp;utm_campaign=430872</link>
      <description><![CDATA[Интеграция приложений — одна из самых значительных статей расходов на ИТ для многих компаний. Кто-то внедряет ESB — централизованный обмен данными между различными информационными системами происходит через интеграционную шину. Другие выбирают распределённую (Distributed) архитектуру, где обмен данными происходит посредством использования системами ресурсов друг друга.<br>
<br>
В своей работе «Промсвязьбанк» уделяет внимание обоим подходам к распределительным системам, а 29 ноября, корпоративный архитектор ПСБ Александр Трехлебов (<a href="https://habr.com/users/holonavt/" class="user_link">holonavt</a>) и руководитель центра инноваций и перспективных технологий банка Андрей Трушкин вынесут преимущества и недостатки этих двух типов архитектур на суд профессиональной общественности в новом формате <a href="http://psbbattle.ru/">митап-баттла ESB vs Distributed</a>. <br>
<br>
<img src="https://habrastorage.org/webt/te/1s/oi/te1soihxxllohi_mmkrumtdcxbg.jpeg"><br> <a href="https://habr.com/post/430872/?utm_source=habrahabr&amp;utm_medium=rss&amp;utm_campaign=430872#habracut">Читать дальше →</a>]]></description>
      
      <pubDate>Fri, 23 Nov 2018 13:12:34 GMT</pubDate>
      <dc:creator>chuvash</dc:creator>
      <category><![CDATA[Блог компании Промсвязьбанк]]></category><category><![CDATA[Mesh-сети]]></category><category><![CDATA[Децентрализованные сети]]></category><category><![CDATA[Конференции]]></category>
      <category><![CDATA[архитектура]]></category><category><![CDATA[ESB]]></category><category><![CDATA[distributed]]></category>
    </item>







  



    <item>
      <title><![CDATA[[Перевод] Переход на облачную платформу Google Cloud (Google Cloud Platform – GCP)]]></title>
      <guid isPermaLink="true">https://habr.com/post/430874/</guid>
      <link>https://habr.com/post/430874/?utm_source=habrahabr&amp;utm_medium=rss&amp;utm_campaign=430874</link>
      <description><![CDATA[<h3 id="chast-2-iz-2">[часть 2 из 2]</h3><br>
<p><a href="https://habr.com/company/southbridge/blog/427347/">[часть 1 из 2]</a></p><br>
<img src="https://habrastorage.org/webt/6m/2c/sx/6m2csxdumpxfx00xrft85x4vdng.png"><br>
<p><br>
</p><br>
<h2 id="kak-nam-eto-udalos">Как нам это удалось</h2><br>
<p>Мы решили <a href="https://blog.hike.in/migration-to-google-cloud-platform-gcp-17c397e564b8">перейти на GCP</a>, чтобы повысить производительность приложений — увеличив при этом масштаб, но без существенных затрат. Весь процесс занял более 2 месяцев. Для решения этой задачи мы сформировали специальную группу инженеров.</p><br>
<p>В этой публикации мы расскажем о выбранном подходе и его реализации, а также о том, как нам удалось достичь главной цели, — осуществить этот процесс максимально гладко и перенести всю инфраструктуру на облачную платформу Google Cloud Platform, не снижая качества обслуживания пользователей.</p><br>
<p><img src="https://habrastorage.org/getpro/habr/post_images/ee6/5d3/693/ee65d3693b51048de4322274109bd23d.png" alt="image"></p> <a href="https://habr.com/post/430874/?utm_source=habrahabr&amp;utm_medium=rss&amp;utm_campaign=430874#habracut">Читать дальше →</a>]]></description>
      
      <pubDate>Fri, 23 Nov 2018 13:11:08 GMT</pubDate>
      <dc:creator>nAbdullin</dc:creator>
      <category><![CDATA[Блог компании Southbridge]]></category><category><![CDATA[DevOps]]></category><category><![CDATA[Серверное администрирование]]></category><category><![CDATA[Системное администрирование]]></category>
      <category>Google</category><category>GCP</category><category>Kubernetes</category>
    </item>



      <item>
      <title><![CDATA[Доступен PhpStorm 2018.3]]></title>
      <guid isPermaLink="true">https://habr.com/post/430878/</guid>
      <link>https://habr.com/post/430878/?utm_source=habrahabr&amp;utm_medium=rss&amp;utm_campaign=430878</link>
      <description><![CDATA[<a href="https://habr.com/company/JetBrains/blog/430878/"><img src="https://habrastorage.org/webt/ub/do/0d/ubdo0dzskyrm7jdjtv8tpmxny4a.jpeg"></a><br>
<br>
Всем привет! Мы рады представить вам третий мажорный релиз PhpStorm в этом году. Добавлены поддержка DQL, PHP CS Fixer, деплоймент на множество хостов одновременно, пулреквесты GitHub, новые рефакторинги и много других улучшений.<br>
<br>
Обзор релиза можно посмотреть на странице “<a href="https://www.jetbrains.com/phpstorm/whatsnew/">What’s new</a>”. Скачать новую версию можно там же или с помощью <a href="https://www.jetbrains.com/toolbox/app/">Toolbox App</a>. Как всегда, доступна 30-дневная пробная версия. Полную же версию могут использовать обладатели <a href="https://www.jetbrains.com/webstorm/buy">действующей подписки</a> на PhpStorm или All Products pack, а также <a href="https://www.jetbrains.com/student/">студенты</a> и <a href="https://www.jetbrains.com/buy/opensource/?product=phpstorm">разработчики</a> проектов с открытым исходным кодом.<br>
<br>
Под катом обзор основных нововведений. (Осторожно, много картинок)<br> <a href="https://habr.com/post/430878/?utm_source=habrahabr&amp;utm_medium=rss&amp;utm_campaign=430878#habracut">Читать дальше →</a>]]></description>
      
      <pubDate>Fri, 23 Nov 2018 13:31:46 GMT</pubDate>
      <dc:creator>pronskiy</dc:creator>
      <category><![CDATA[Блог компании JetBrains]]></category><category><![CDATA[PHP]]></category><category><![CDATA[Разработка веб-сайтов]]></category>
      <category><![CDATA[PHP]]></category><category><![CDATA[PhpStorm]]></category><category><![CDATA[DQL]]></category><category><![CDATA[PHP CS Fixer]]></category><category><![CDATA[PHP_CodeSniffer]]></category><category><![CDATA[JetBrains]]></category>
    </item>







      <item>
      <title><![CDATA[Как доить коров роботами и сделать на этом промышленный стартап. История разработки R-SEPT]]></title>
      <guid isPermaLink="true">https://habr.com/post/430740/</guid>
      <link>https://habr.com/post/430740/?utm_source=habrahabr&amp;utm_medium=rss&amp;utm_campaign=430740</link>
      <description><![CDATA[<img src="https://habrastorage.org/webt/cp/op/89/cpop89njul1noiph5pmqdcpdmke.jpeg"><br>
<br>
В 2017 году в СМИ звучала крайне интересная история про стартап, который роботизирует доение коров на промышленных молочных фермах. Компания называется R-SEPT, и тогда она получила 10 миллионов рублей инвестиций. Но год прошел, а новостей, что произошло дальше, все нет. Мы связались с Алексеем Хахуновым (<a href="https://habr.com/users/alexeihahunov/" class="user_link">AlexeiHahunov</a>), основателем стартапа и поговорили о разработке. Оказывается весь год его команда доводила прототип робота до ума, и как раз неделю назад провела первые полевые испытания на ферме.<br>
<br>
Под катом — история, как студент-робототехник, выросший на родительской ферме, превратил университетский диплом в промышленный стартап, как собирал с друзьями первые манипуляторы, а потом выходил на уровень государственных программ по роботизации сельского хозяйства. Ну и самое главное — чем железная рука робота и машинное зрение лучше живой доярки. <br> <a href="https://habr.com/post/430740/?utm_source=habrahabr&amp;utm_medium=rss&amp;utm_campaign=430740#habracut">Читать дальше →</a>]]></description>
      
      <pubDate>Fri, 23 Nov 2018 12:45:14 GMT</pubDate>
      <dc:creator>arttom</dc:creator>
      <category><![CDATA[DIY или Сделай сам]]></category><category><![CDATA[Машинное обучение]]></category><category><![CDATA[Развитие стартапа]]></category><category><![CDATA[Разработка робототехники]]></category><category><![CDATA[Робототехника]]></category>
      <category><![CDATA[r-sept]]></category><category><![CDATA[робототехника]]></category>
    </item>







      <item>
      <title><![CDATA[«Монстры в играх — как заставить игрока тебя ненавидеть»]]></title>
      <guid isPermaLink="true">https://habr.com/post/430862/</guid>
      <link>https://habr.com/post/430862/?utm_source=habrahabr&amp;utm_medium=rss&amp;utm_campaign=430862</link>
      <description><![CDATA[<div style="text-align:center;"><img src="https://habrastorage.org/webt/mn/ug/lp/mnuglpqe9e10zvmvg3df8u_kvzy.jpeg"></div><br>
 <b>Привет дорогой читатель</b>, в предыдущем материале мы разобрали, на какой дистанции атакуют монстры, какие вариации и комбинации внутри категорий бывают<br>
и какие противники наиболее раздражающие для большинства игроков.<br>
<br>
<b>Но прежде чем перейти к пятой статье цикла — рекомендую ознакомится с предыдущими материалами по ссылкам:</b><br>
<br>
ч. 1 <a href="https://habr.com/post/429406/">«Монстры в играх или как создать страх»</a><br>
ч. 2 <a href="https://habr.com/post/429630/">«Монстры в играх или делаем страх разнообразным»</a><br>
ч. 3 <a href="https://habr.com/post/429860/">«Монстры в играх или как удивить игрока»</a><br>
ч. 4 <a href="https://habr.com/post/430272/">«Монстры в играх или 15 см достаточно для атаки»</a><br>
<br>
Сегодня мы научимся правильно комбинировать монстров для плотного и интересного геймплея, приведем конкретные примеры и визуализируем их. <br>
<br>
<blockquote>О том как их расставлять мы будем говорить в материалах по дизайну уровней, в отдельном цикле статей</blockquote><br>
Хотите делать крутые игры с термоядерным геймплеем? Хотите подарить пользователю незабываемые эмоции и приступы слюновыделения при виде ваших тайтлов?<br>
<br>
<h4>Добро пожаловать под кат</h4><br> <a href="https://habr.com/post/430862/?utm_source=habrahabr&amp;utm_medium=rss&amp;utm_campaign=430862#habracut">Читать дальше →</a>]]></description>
      
      <pubDate>Fri, 23 Nov 2018 11:12:04 GMT</pubDate>
      <dc:creator>Hzpriezz</dc:creator>
      <category><![CDATA[Дизайн игр]]></category><category><![CDATA[Игры и игровые приставки]]></category><category><![CDATA[Разработка игр]]></category>
      <category><![CDATA[assasins creed]]></category><category><![CDATA[doom 2016]]></category><category><![CDATA[начинающему]]></category><category><![CDATA[личный опыт]]></category><category><![CDATA[дизайн противников]]></category><category><![CDATA[разработка игр]]></category><category><![CDATA[dark souls]]></category><category><![CDATA[учимся на ошибках]]></category><category><![CDATA[примеры]]></category><category><![CDATA[цикл статей]]></category><category><![CDATA[монстры в играх]]></category><category><![CDATA[монстры]]></category><category><![CDATA[геймдизайн]]></category><category><![CDATA[геймдизайнер обучение]]></category>
    </item>







      <item>
      <title><![CDATA[Нравственные нормы нестов]]></title>
      <guid isPermaLink="true">https://habr.com/post/430816/</guid>
      <link>https://habr.com/post/430816/?utm_source=habrahabr&amp;utm_medium=rss&amp;utm_campaign=430816</link>
      <description><![CDATA[<div style="text-align:center;"><img src="https://habrastorage.org/webt/_i/vp/zj/_ivpzj_exumjdfsbkv2-upy15p4.png"></div><br>
Тут сейчас будут рассуждения на тему изменений нравственности, вызванных чрезмерно долгой жизнью, настолько долгой, что это уже не лезет ни в какие ворота. <br>
<br>
О будущей нравственности нестареющих людей — нестов, в двух словах можно сказать следующее. После того как люди научатся откатывать процесс старения, в головах произойдет антропоцентристский сдвиг: «старение преодолено — непобедимая смерть отступила». Начнется новая эра — война с самой смертью (а у людей много разнообразных шансов окочуриться, даже у самых нестареющих), а не как сейчас — война со страхом смерти (конечно научились «воявать со страхом», терпеть-то кому смелости не доставало — уговариваем себя что не боимся смерти… а сами жиденькую слюну глотаем при виде первого настоящего кадавра).<br>
<br>
Нет, не будут люди расслабляться и деградировать, как мечтают недруги человечества. У людей же враг появится настоящий — смерть. Это сейчас мы бедненькие её, Смерть, терпим (она же непобедимая, куда уж нам), а вот как старение победим… По-другому все будет. Можно сравнить с ситуацией, когда полные башмаки гвоздей (полно проблем у живущих), а идти таки надо (жить): пару шагов еще можно потерпеть (прожить несколько десятков лет и подавиться мацой, делов-то), а если в поход далекий (на тысячелетия), то придется гвозди из башмаков доставать, да. Или с ситуацией, когда медленно движется лава на деревню — люди не просто закатывают глаза и начинают бухать и трахаться, они хватают бульдозеры и успевают, выкапывают рвы. Они вынуждены и у них есть на это время. Долгая жизнь будет вынуждать нестов вести себя так, а не иначе. Помните, как в Великую Отечественную? Гопник и трахарь — братья на передовой, в тылу за воровство расстрел, и ботаники ночи не спят — самолеты и пулеметы изобретают новые. Вот то же самое будет, когда увидят люди, что и у самой Смерти юшка из сопатки капает, если хряснуть как следует.<br>
<br>
И вот мы плавно переходим к вопросу «Чем отличается этика от морали».<br> <a href="https://habr.com/post/430816/?utm_source=habrahabr&amp;utm_medium=rss&amp;utm_campaign=430816#habracut">Ну и чем же отличается этика от морали?</a>]]></description>
      
      <pubDate>Thu, 22 Nov 2018 22:43:52 GMT</pubDate>
      <dc:creator>FransuaMaryDelone</dc:creator>
      <category><![CDATA[Будущее здесь]]></category>
      <category><![CDATA[Философия]]></category><category><![CDATA[культура]]></category><category><![CDATA[бессмертие]]></category><category><![CDATA[несты]]></category>
    </item>











  </channel>
</rss>
