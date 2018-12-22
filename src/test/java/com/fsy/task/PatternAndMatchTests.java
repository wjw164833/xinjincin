package com.fsy.task;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternAndMatchTests {
    public static void main(String[] args) {
        String jsonQuestionHtml = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">" +
                "<html xmlns=\"http://www.w3.org/1999/xhtml\">" +
                "<head>" +
                "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />" +
                "<link type=\"text/css\" rel=\"stylesheet\" href=\"../../css/head.css?v=20181107160944\" />" +
                "<link type=\"text/css\" rel=\"stylesheet\" href=\"../../css/cdep_common.css?v=20181107160944\" />" +
                "<link rel=\"stylesheet\" href=\"../../css/stu_jxjh_style.css\" type=\"text/css\"></link>" +
                "" +
                "<script type=\"text/javascript\" src=\"http://static.njcedu.com/JSLib/CDOJS/Utility.js?v=20181107160944\"></script>" +
                "<script type=\"text/javascript\" src=\"http://static.njcedu.com/JSLib/CDOJS/HashMap.js?v=20181107160944\"></script>" +
                "<script type=\"text/javascript\" src=\"http://static.njcedu.com/JSLib/CDOJS/CDO.js?v=20181107160944\"></script>" +
                "<script type=\"text/javascript\" src=\"http://static.njcedu.com/JSLib/CDOJS/JSON.js?v=20181107160944\"></script>" +
                "<script type=\"text/javascript\" src=\"http://static.njcedu.com/JSLib/CDOJS/HttpClient.js?v=20181107160944\"></script>" +
                "<script type=\"text/javascript\" src=\"http://static.njcedu.com/js/cookie.js?v=20181107160944\"></script>" +
                "<script type=\"text/javascript\" src=\"http://static.njcedu.com/js/jquery-1.7.2.min.js?v=20181107160944\" ></script>" +
                "" +
                "<script type=\"text/javascript\" src=\"pager.js?v=20181107160944\" ></script>" +
                "<script>" +
                "function CloseWebPage() {" +
                "if (navigator.userAgent.indexOf(\"MSIE\") > 0) {" +
                "if (navigator.userAgent.indexOf(\"MSIE 6.0\") > 0) {" +
                "window.opener = null;" +
                "window.close();" +
                "} else {" +
                "window.open('', '_top');" +
                "window.top.close();" +
                "}" +
                "} else if (navigator.userAgent.indexOf(\"Firefox\") > 0) {" +
                "window.location.href = 'about:blank ';" +
                "} else {" +
                "window.location.href = 'about:blank ';" +
                "}" +
                "}" +
                "</script>" +
                "<title id=\"page_title\"> - 新锦成就业教育平台</title>" +
                "</head>" +
                "<body oncontextmenu=\"window.event.returnValue=false\" onselectstart=\"return false\" oncopy=\"return false;\" oncut=\"return false;\">" +
                "" +
                "" +
                "" +
                "" +
                "" +
                "<!-- 分数处理 -->" +
                "<!-- 分数处理 end -->" +
                "<center>" +
                "<div class=\"stu_ks\">" +
                "<h1 class=\"stu_ks_tit\">" +
                "2015级学生职业发展与就业指导课学习任务（五）考试" +
                "</h1>" +
                "    <p class=\"col999 ta_cen\">" +
                "    总分 / 通过分数：100 / 0分" +
                "    </p>" +
                "    <input type=\"hidden\" id=\"dtEndTime\" value=\"2018-12-31 23:59:59\" />" +
                "    <input type=\"hidden\" id=\"dtStartTime\" value=\"2018-10-08 09:56:54\" />" +
                "    <input type=\"hidden\" id=\"nTimeLength\" value=\"120\" />" +
                "    <input type=\"hidden\" id=\"lSchoolId\" value=\"585\" />" +
                "    <input type=\"hidden\" id=\"lUserId\" value=\"19000009364\" />" +
                "    <input type=\"hidden\" id=\"nExaminType\" value=\"0\" />" +
                "    <input type=\"hidden\" id=\"lExaminId\" value=\"29270000074\" />" +
                "    <input type=\"hidden\" id=\"lPlanId\" value=\"29270000050\" />" +
                "    <input type=\"hidden\" id=\"nUsedTime\" value=\"0\" />" +
                "    <input type=\"hidden\" id=\"nExaminState\" value=\"0\" />" +
                "    <input type=\"hidden\" id=\"strExaminName\" value=\"2015级学生职业发展与就业指导课学习任务（五）考试\" />" +
                "    <script>" +
                "    try{" +
                "    var page_title = document.getElementById(\"page_title\");" +
                "if(page_title != null){" +
                "document.title = document.getElementById(\"strExaminName\").value + page_title.innerText;" +
                "}" +
                "    }catch(e){" +
                "    }" +
                "</script>" +
                "</div>" +
                "</center>" +
                "" +
                "" +
                "" +
                "<div class=\"xk_con\">" +
                "    <div class=\"content\">" +
                "    <div class=\"ks_left ptb30\">" +
                "        <h1 class=\"ks_left_tit\" id=\"keguanti\">" +
                "    客观题" +
                "    <span class=\"col666 fs14 ml8\">" +
                "    (共30题,每题2分)" +
                "    </span>" +
                "    </h1>" +
                "                        <ul class=\"mt30\">" +
                "                <li class=\"ks_t_xh\">1</li>" +
                "                <li class=\"ks_tm mb10\"  id=\"2538\"></li>" +
                "                <div class=\"clear\"></div>" +
                "                <div id=\"option2538\">" +
                "                </div>" +
                "                <script type=\"text/javascript\">" +
                "var jsonQuestion={title:\"劳动者在同一用人单位连续工作满（）年后提出与用人单位订立无固定期限劳动合同的，应当订立无固定期限劳动合同。\",options:[{name:\"A\",text:\"三 \"},{name:\"B\",text:\"五 \"},{name:\"C\",text:\"八 \"},{name:\"D\",text:\"十\"}]};" +
                " showQuestion(jsonQuestion,2538,\"0\");" +
                "</script>" +
                "            </ul>" +
                "                        <ul class=\"mt30\">" +
                "                <li class=\"ks_t_xh\">2</li>" +
                "                <li class=\"ks_tm mb10\"  id=\"2560\"></li>" +
                "                <div class=\"clear\"></div>" +
                "                <div id=\"option2560\">" +
                "                </div>" +
                "                <script type=\"text/javascript\">" +
                "var jsonQuestion={title:\"（）是毕业生和用人单位在签订劳动合同前，双方确定就业意向和权益的依据，其具有民事合同的性质及相应的法律责任与权利，但又不等同于劳动合同。\",options:[{name:\"A\",text:\"毕业生就业报到证 \"},{name:\"B\",text:\"毕业生就业推荐表 \"},{name:\"C\",text:\"毕业生就业协议书 \"},{name:\"D\",text:\"户口迁移证\"}]};" +
                " showQuestion(jsonQuestion,2560,\"0\");" +
                "</script>" +
                "            </ul>" +
                "                        <ul class=\"mt30\">" +
                "                <li class=\"ks_t_xh\">3</li>" +
                "                <li class=\"ks_tm mb10\"  id=\"2569\"></li>" +
                "                <div class=\"clear\"></div>" +
                "                <div id=\"option2569\">" +
                "                </div>" +
                "                <script type=\"text/javascript\">" +
                "var jsonQuestion={title:\"现今，人才市场良莠不齐，存在一些非法的人才中介行为。请指出下列哪一项是不合法的：\",options:[{name:\"A\",text:\"外地人才网站擅自在异地从事人才中介业务 \"},{name:\"B\",text:\"本地人才机构经批准在当地举办人才交流会 \"},{name:\"C\",text:\"人才引进过程中，按照一定标准严格打分，控制人才流动 \"},{name:\"D\",text:\"职业介绍机构按照规定从事职业介绍行为\"}]};" +
                " showQuestion(jsonQuestion,2569,\"0\");" +
                "</script>" +
                "            </ul>" +
                "                        <ul class=\"mt30\">" +
                "                <li class=\"ks_t_xh\">4</li>" +
                "                <li class=\"ks_tm mb10\"  id=\"2542\"></li>" +
                "                <div class=\"clear\"></div>" +
                "                <div id=\"option2542\">" +
                "                </div>" +
                "                <script type=\"text/javascript\">" +
                "var jsonQuestion={title:\"关于大学生就业权益，下列表述不正确的是：\",options:[{name:\"A\",text:\"就业信息是毕业生择业成功的前提和关键，毕业生具有获取信息的权利 \"},{name:\"B\",text:\"大学生求职过程中，具有接受就业指导和被推荐的权利 \"},{name:\"C\",text:\"求职过程中，大学生有获取公平待遇及违约求偿的权利 \"},{name:\"D\",text:\"以上说法都不对\"}]};" +
                " showQuestion(jsonQuestion,2542,\"0\");" +
                "</script>" +
                "            </ul>" +
                "                        <ul class=\"mt30\">" +
                "                <li class=\"ks_t_xh\">5</li>" +
                "                <li class=\"ks_tm mb10\"  id=\"2555\"></li>" +
                "                <div class=\"clear\"></div>" +
                "                <div id=\"option2555\">" +
                "                </div>" +
                "                <script type=\"text/javascript\">" +
                "var jsonQuestion={title:\"毕业生落实工作办理派遣手续后，需凭（）办理户口迁移证。\",options:[{name:\"A\",text:\"毕业生就业报到证 \"},{name:\"B\",text:\"毕业生就业推荐表 \"},{name:\"C\",text:\"毕业生就业协议书 \"},{name:\"D\",text:\"户口迁移证\"}]};" +
                " showQuestion(jsonQuestion,2555,\"0\");" +
                "</script>" +
                "            </ul>" +
                "                        <ul class=\"mt30\">" +
                "                <li class=\"ks_t_xh\">6</li>" +
                "                <li class=\"ks_tm mb10\"  id=\"2601\"></li>" +
                "                <div class=\"clear\"></div>" +
                "                <div id=\"option2601\">" +
                "                </div>" +
                "                <script type=\"text/javascript\">" +
                "var jsonQuestion={title:\"人际交往的成功与否，在一定意义上，取决于别人在你心中的位置如何。你认为这种说法：\",options:[{name:\"A\",text:\"正确 \"},{name:\"B\",text:\"错误\"}]};" +
                " showQuestion(jsonQuestion,2601,\"0\");" +
                "</script>" +
                "            </ul>" +
                "                        <ul class=\"mt30\">" +
                "                <li class=\"ks_t_xh\">7</li>" +
                "                <li class=\"ks_tm mb10\"  id=\"2644\"></li>" +
                "                <div class=\"clear\"></div>" +
                "                <div id=\"option2644\">" +
                "                </div>" +
                "                <script type=\"text/javascript\">" +
                "var jsonQuestion={title:\"小王是一家公司的职员，他的大部分工作都是为他人服务，或者在需要的时候做一些应急的事情。那么你认为有关小王工作的描述中，正确的是：\",options:[{name:\"A\",text:\"他的工作无法做计划 \"},{name:\"B\",text:\"他只需要做好自己应该做的 \"},{name:\"C\",text:\"他没有必要做计划 \"},{name:\"D\",text:\"对一些重复出现的事情可以做计划\"}]};" +
                " showQuestion(jsonQuestion,2644,\"0\");" +
                "</script>" +
                "            </ul>" +
                "                        <ul class=\"mt30\">" +
                "                <li class=\"ks_t_xh\">8</li>" +
                "                <li class=\"ks_tm mb10\"  id=\"2659\"></li>" +
                "                <div class=\"clear\"></div>" +
                "                <div id=\"option2659\">" +
                "                </div>" +
                "                <script type=\"text/javascript\">" +
                "var jsonQuestion={title:\"小刘在大学里面是学生会主席，非常善于演讲。进入工作岗位之后，不管有事没事，就喜欢大讲自己的光辉历史。单位前辈让他帮忙做事时，他总说没空。在一次业务陈述会上，小刘发挥所长陈述了几十种想法，但是当上司吩咐他去做时，他却做的乱七八糟。这说明了什么道理，表述最准确的是：\",options:[{name:\"A\",text:\"小刘能提出几十种想法，说明他工作有激情 \"},{name:\"B\",text:\"小刘急于想得到同事的认可是好的，但是方法不对，不应该好高骛远 \"},{name:\"C\",text:\"小刘初入职场不可眼高手低，光说不干，要踏踏实实从小事做起 \"},{name:\"D\",text:\"以上说法都不对\"}]};" +
                " showQuestion(jsonQuestion,2659,\"0\");" +
                "</script>" +
                "            </ul>" +
                "                        <ul class=\"mt30\">" +
                "                <li class=\"ks_t_xh\">9</li>" +
                "                <li class=\"ks_tm mb10\"  id=\"2602\"></li>" +
                "                <div class=\"clear\"></div>" +
                "                <div id=\"option2602\">" +
                "                </div>" +
                "                <script type=\"text/javascript\">" +
                "var jsonQuestion={title:\"职场新人常常由于缺乏自我意识，而认为自己很渺小，无法真正看清自己。应怎样调整这种“注定失败”的不良职场心理？以下说法正确的是：\",options:[{name:\"A\",text:\"经常使用良好的、积极的、建设性的语汇暗示自己，增强自信心 \"},{name:\"B\",text:\"平时尽量从“为什么能做到”方面着想，而不应围绕“为什么无法做到”打转 \"},{name:\"C\",text:\"脑子里经常想着“我将要成功”，并努力寻找各种“有助于成功”的方法 \"},{name:\"D\",text:\"以上都对\"}]};" +
                " showQuestion(jsonQuestion,2602,\"0\");" +
                "</script>" +
                "            </ul>" +
                "                        <ul class=\"mt30\">" +
                "                <li class=\"ks_t_xh\">10</li>" +
                "                <li class=\"ks_tm mb10\"  id=\"2635\"></li>" +
                "                <div class=\"clear\"></div>" +
                "                <div id=\"option2635\">" +
                "                </div>" +
                "                <script type=\"text/javascript\">" +
                "var jsonQuestion={title:\"大学生到新单位后，首先要合群，努力使自己融入新的集体当中，尤其要学会察言观色，建立自己的小团体或是加入别人的小圈子。请判断这一说法的正误。\",options:[{name:\"A\",text:\"正确 \"},{name:\"B\",text:\"错误\"}]};" +
                " showQuestion(jsonQuestion,2635,\"0\");" +
                "</script>" +
                "            </ul>" +
                "                        <ul class=\"mt30\">" +
                "                <li class=\"ks_t_xh\">11</li>" +
                "                <li class=\"ks_tm mb10\"  id=\"2631\"></li>" +
                "                <div class=\"clear\"></div>" +
                "                <div id=\"option2631\">" +
                "                </div>" +
                "                <script type=\"text/javascript\">" +
                "var jsonQuestion={title:\"在适当的时候阐明自己在某项工作中起到的作用和承担的责任，这样可以避免你在那里苦苦卖力，而老板却慧眼不识。这种做法是否正确？\",options:[{name:\"A\",text:\"正确 \"},{name:\"B\",text:\"错误\"}]};" +
                " showQuestion(jsonQuestion,2631,\"0\");" +
                "</script>" +
                "            </ul>" +
                "                        <ul class=\"mt30\">" +
                "                <li class=\"ks_t_xh\">12</li>" +
                "                <li class=\"ks_tm mb10\"  id=\"2590\"></li>" +
                "                <div class=\"clear\"></div>" +
                "                <div id=\"option2590\">" +
                "                </div>" +
                "                <script type=\"text/javascript\">" +
                "var jsonQuestion={title:\"人脉资源根据所起作用的不同，可以分为：\",options:[{name:\"A\",text:\"血缘人脉资源、地缘人脉资源、学缘人脉资源等 \"},{name:\"B\",text:\"政府人脉资源、金融人脉资源、行业人脉资源等 \"},{name:\"C\",text:\"核心层人脉资源、紧密层人脉资源、松散层人脉资源等 \"},{name:\"D\",text:\"现在时人脉资源和将来时人脉资源等\"}]};" +
                " showQuestion(jsonQuestion,2590,\"0\");" +
                "</script>" +
                "            </ul>" +
                "                        <ul class=\"mt30\">" +
                "                <li class=\"ks_t_xh\">13</li>" +
                "                <li class=\"ks_tm mb10\"  id=\"2591\"></li>" +
                "                <div class=\"clear\"></div>" +
                "                <div id=\"option2591\">" +
                "                </div>" +
                "                <script type=\"text/javascript\">" +
                "var jsonQuestion={title:\"赫茨伯格的双因素理论把影响员工满意度的工作环境或工作方面的因素称之为：\",options:[{name:\"A\",text:\"激励因素\"},{name:\"B\",text:\"保健因素\"},{name:\"C\",text:\"不满意因素\"},{name:\"D\",text:\"满意因素\"}]};" +
                " showQuestion(jsonQuestion,2591,\"0\");" +
                "</script>" +
                "            </ul>" +
                "                        <ul class=\"mt30\">" +
                "                <li class=\"ks_t_xh\">14</li>" +
                "                <li class=\"ks_tm mb10\"  id=\"2662\"></li>" +
                "                <div class=\"clear\"></div>" +
                "                <div id=\"option2662\">" +
                "                </div>" +
                "                <script type=\"text/javascript\">" +
                "var jsonQuestion={title:\"小王刚到报社上班时，干劲十足。虽说是在见习期，但是干的活不比正式员工少。可是发薪水时，他发现自己拿到的还没有正式员工的一半多，激情一下子荡然无存。面对这种情况，小王应该如何应对，表述最准确的是：\",options:[{name:\"A\",text:\"小王应该找到领导，要求补足与正式员工的薪酬差额 \"},{name:\"B\",text:\"小王应该马上辞职，这种不尊重别人劳动的单位不值得待下去 \"},{name:\"C\",text:\"小王不应该计较一时的收入，应该更加努力增强自身能力，一定可以拿到应得的报酬 \"},{name:\"D\",text:\"拿多少钱干多少活，小王以后应该少干活\"}]};" +
                " showQuestion(jsonQuestion,2662,\"0\");" +
                "</script>" +
                "            </ul>" +
                "                        <ul class=\"mt30\">" +
                "                <li class=\"ks_t_xh\">15</li>" +
                "                <li class=\"ks_tm mb10\"  id=\"2630\"></li>" +
                "                <div class=\"clear\"></div>" +
                "                <div id=\"option2630\">" +
                "                </div>" +
                "                <script type=\"text/javascript\">" +
                "var jsonQuestion={title:\"每个人在职场中，由于各种原因，都有可能遇到不公平的待遇，尤其是对于刚刚步入岗位的大学生，受到不公平的待遇是很常见的事情。对于这种情况，下列做法不当的是：\",options:[{name:\"A\",text:\"当遇到不公平的待遇时，勿过分计较个人的得失，应从大局出发，让理智有效的控制感情，平和心态，这样有助于避免不良心态的滋生和发展 \"},{name:\"B\",text:\"有意识的改变对比的内容，努力创造优势，改变不利的对比反差，从而达到心态平和 \"},{name:\"C\",text:\"在遇到不公平的待遇时，应该据理力争，不能改变自己的原则，不惜与老板和公司翻脸，这样才能够维护自己的合法权益不受损害 \"},{name:\"D\",text:\"当在职场中受到不公平的待遇时，切忌义气用事，而应冷静理智的处理，保持良好的形象和风度，寻找得体的选择\"}]};" +
                " showQuestion(jsonQuestion,2630,\"0\");" +
                "</script>" +
                "            </ul>" +
                "                        <ul class=\"mt30\">" +
                "                <li class=\"ks_t_xh\">16</li>" +
                "                <li class=\"ks_tm mb10\"  id=\"3956\"></li>" +
                "                <div class=\"clear\"></div>" +
                "                <div id=\"option3956\">" +
                "                </div>" +
                "                <script type=\"text/javascript\">" +
                "var jsonQuestion={title:\"高效实现从学生到职业人角色转变的方法不包括：\",options:[{name:\"A\",text:\"主动学习，在工作中掌握大量的新知识\"},{name:\"B\",text:\"积极积累自己的人力资本\"},{name:\"C\",text:\"主动发送能力信号，让同事、领导较快认识到自己的与众不同\"},{name:\"D\",text:\"默默耕耘，从不反思自我，也不过问结果\"}]};" +
                " showQuestion(jsonQuestion,3956,\"0\");" +
                "</script>" +
                "            </ul>" +
                "                        <ul class=\"mt30\">" +
                "                <li class=\"ks_t_xh\">17</li>" +
                "                <li class=\"ks_tm mb10\"  id=\"2665\"></li>" +
                "                <div class=\"clear\"></div>" +
                "                <div id=\"option2665\">" +
                "                </div>" +
                "                <script type=\"text/javascript\">" +
                "var jsonQuestion={title:\"相互接纳，是指组织与新雇员个人之间的相互关系。关于相互接纳，以下哪项表述是不正确的：\",options:[{name:\"A\",text:\"双方必须互相认同和接纳，只有单方的认同，相互接纳便不存在 \"},{name:\"B\",text:\"相互接纳是一种心理契约，是一个过程 \"},{name:\"C\",text:\"相互接纳这一阶段无确定期限，期限的长短，受工作性质、部门类型、上司风格、组织文化、新雇员的实际等诸多因素的影响 \"},{name:\"D\",text:\"以上说法都不对\"}]};" +
                " showQuestion(jsonQuestion,2665,\"0\");" +
                "</script>" +
                "            </ul>" +
                "                        <ul class=\"mt30\">" +
                "                <li class=\"ks_t_xh\">18</li>" +
                "                <li class=\"ks_tm mb10\"  id=\"2670\"></li>" +
                "                <div class=\"clear\"></div>" +
                "                <div id=\"option2670\">" +
                "                </div>" +
                "                <script type=\"text/javascript\">" +
                "var jsonQuestion={title:\" 团队与群体不同，群体可能只是一群乌合之众，而团队必须具备高度的战斗能力，为此，团队需要满足一些条件。以下不属于团队必要条件的是？\",options:[{name:\"A\",text:\"自主性 \"},{name:\"B\",text:\"思考性 \"},{name:\"C\",text:\"合作性 \"},{name:\"D\",text:\"一致性\"}]};" +
                " showQuestion(jsonQuestion,2670,\"0\");" +
                "</script>" +
                "            </ul>" +
                "                        <ul class=\"mt30\">" +
                "                <li class=\"ks_t_xh\">19</li>" +
                "                <li class=\"ks_tm mb10\"  id=\"2608\"></li>" +
                "                <div class=\"clear\"></div>" +
                "                <div id=\"option2608\">" +
                "                </div>" +
                "                <script type=\"text/javascript\">" +
                "var jsonQuestion={title:\"刚参加工作的大学生在和自己同学交流的过程中，很多人都表现出对目前工作的不满和对别人工作的羡慕。当出现这种浮躁心态的时候，应该学会站在企业和社会现实的角度去考虑一些问题。这一说法，你认为：\",options:[{name:\"A\",text:\"正确 \"},{name:\"B\",text:\"错误\"}]};" +
                " showQuestion(jsonQuestion,2608,\"0\");" +
                "</script>" +
                "            </ul>" +
                "                        <ul class=\"mt30\">" +
                "                <li class=\"ks_t_xh\">20</li>" +
                "                <li class=\"ks_tm mb10\"  id=\"2626\"></li>" +
                "                <div class=\"clear\"></div>" +
                "                <div id=\"option2626\">" +
                "                </div>" +
                "                <script type=\"text/javascript\">" +
                "var jsonQuestion={title:\"大学生初涉职场，对现实的工作和人际关系等新面临的问题产生困扰，心理上会有一定的落差。为了避免职业心理危机，大学生要做到：\",options:[{name:\"A\",text:\"尽快平静心情，认真了解企业文化\"},{name:\"B\",text:\"不急躁，做事时分清轻重缓急\"},{name:\"C\",text:\"克服以前的自由心理，遵守公司章程\"},{name:\"D\",text:\"以上说法都正确\"}]};" +
                " showQuestion(jsonQuestion,2626,\"0\");" +
                "</script>" +
                "            </ul>" +
                "                        <ul class=\"mt30\">" +
                "                <li class=\"ks_t_xh\">21</li>" +
                "                <li class=\"ks_tm mb10\"  id=\"2724\"></li>" +
                "                <div class=\"clear\"></div>" +
                "                <div id=\"option2724\">" +
                "                </div>" +
                "                <script type=\"text/javascript\">" +
                "var jsonQuestion={title:\"企业的竞争归根结底是学习能力的竞争。造就良好的学习能力，首先要营造良好的学习氛围，树立全面学习、终身学习的理念至关重要。关于终身学习与企业成长的观点，下列说法正确的是：\",options:[{name:\"A\",text:\"学习是智慧的源泉，是获取知识的唯一途径，是实现自我超越和人生价值的必由之路 \"},{name:\"B\",text:\"随着经济的发展和科技的进步，社会竞争日趋激烈，知识更新越来越快，人们渴求知识，渴望提高的心情也越来越迫切 \"},{name:\"C\",text:\"要从关系企业生存和发展的高度、从实现企业目标和实现个人价值相统一的角度，倡导全面学习、终身学习的理念，在企业内形成浓厚的学习氛围 \"},{name:\"D\",text:\"以上说法都正确\"}]};" +
                " showQuestion(jsonQuestion,2724,\"0\");" +
                "</script>" +
                "            </ul>" +
                "                        <ul class=\"mt30\">" +
                "                <li class=\"ks_t_xh\">22</li>" +
                "                <li class=\"ks_tm mb10\"  id=\"2704\"></li>" +
                "                <div class=\"clear\"></div>" +
                "                <div id=\"option2704\">" +
                "                </div>" +
                "                <script type=\"text/javascript\">" +
                "var jsonQuestion={title:\"有关人际关系的描述，下列说法正确的是：\",options:[{name:\"A\",text:\"和谐的人际关系，有利于职场新人消除对新环境的陌生感、孤独感，顺利度过适应期 \"},{name:\"B\",text:\"良好的人际关系，有利于员工心气顺、信心增、工作舒心、生活惬意 \"},{name:\"C\",text:\"良好的人际关系，有利于增强团结，形成上下左右齐心协力、共谋发展的良好局面，既利于集体，也利于个人的生存和发展 \"},{name:\"D\",text:\"以上说法都正确\"}]};" +
                " showQuestion(jsonQuestion,2704,\"0\");" +
                "</script>" +
                "            </ul>" +
                "                        <ul class=\"mt30\">" +
                "                <li class=\"ks_t_xh\">23</li>" +
                "                <li class=\"ks_tm mb10\"  id=\"2706\"></li>" +
                "                <div class=\"clear\"></div>" +
                "                <div id=\"option2706\">" +
                "                </div>" +
                "                <script type=\"text/javascript\">" +
                "var jsonQuestion={title:\"关于职业素质，下列说法正确的是：\",options:[{name:\"A\",text:\"职业素质是劳动者在一定的生理和心理条件的基础上，通过教育、劳动实践和自我修养等途径而形成和发展起来的 \"},{name:\"B\",text:\"职业素质是在职业活动中发挥重要作用的内在基本品质 \"},{name:\"C\",text:\"职业素质的特征：职业性、稳定性、内在性、整体性、发展性 \"},{name:\"D\",text:\"以上说法都正确\"}]};" +
                " showQuestion(jsonQuestion,2706,\"0\");" +
                "</script>" +
                "            </ul>" +
                "                        <ul class=\"mt30\">" +
                "                <li class=\"ks_t_xh\">24</li>" +
                "                <li class=\"ks_tm mb10\"  id=\"2688\"></li>" +
                "                <div class=\"clear\"></div>" +
                "                <div id=\"option2688\">" +
                "                </div>" +
                "                <script type=\"text/javascript\">" +
                "var jsonQuestion={title:\"一座破旧的庙里住着两只蜘蛛，一只在屋檐下，一只在佛龛上。一天，旧庙的屋顶塌掉了，幸运的是，两只蜘蛛没有受伤，他们依然在自己的地盘上忙碌地编织着蜘蛛网。没过几天，佛龛上的蜘蛛发现自己的网总是被搞破。一只小鸟飞过，一阵小风刮起，都会让它忙着修上半天。它去问屋檐下的蜘蛛：“我们的丝没有区别，工作的地方也没有改变。为什么我的网总会破，而你的却没事呢？”屋檐下的蜘蛛笑着说：“难道你没有发现我们头上的屋檐已经没有了吗？”这则寓言故事给我们怎样的职场提示？\",options:[{name:\"A\",text:\"埋头工作固然很重要，但经常思考工作中的难题，发现问题的根源更加重要 \"},{name:\"B\",text:\"做一件事时要制定一个日程安排表，不实现目标决不罢休 \"},{name:\"C\",text:\"职场能力提升不能忘记打牢基础，如果只想往上砌砖，不顾根基，总有一天塔会倒塌 \"},{name:\"D\",text:\"以上都对\"}]};" +
                " showQuestion(jsonQuestion,2688,\"0\");" +
                "</script>" +
                "            </ul>" +
                "                        <ul class=\"mt30\">" +
                "                <li class=\"ks_t_xh\">25</li>" +
                "                <li class=\"ks_tm mb10\"  id=\"3216\"></li>" +
                "                <div class=\"clear\"></div>" +
                "                <div id=\"option3216\">" +
                "                </div>" +
                "                <script type=\"text/javascript\">" +
                "var jsonQuestion={title:\"下列表述中，不属于良好的职业态度的是：\",options:[{name:\"A\",text:\"有责任心 \"},{name:\"B\",text:\"对企业忠诚 \"},{name:\"C\",text:\"不服从上司指令 \"},{name:\"D\",text:\"不学历造假\"}]};" +
                " showQuestion(jsonQuestion,3216,\"0\");" +
                "</script>" +
                "            </ul>" +
                "                        <ul class=\"mt30\">" +
                "                <li class=\"ks_t_xh\">26</li>" +
                "                <li class=\"ks_tm mb10\"  id=\"2713\"></li>" +
                "                <div class=\"clear\"></div>" +
                "                <div id=\"option2713\">" +
                "                </div>" +
                "                <script type=\"text/javascript\">" +
                "var jsonQuestion={title:\"从业人员一系列道德行为表现出的比较稳定的特征和倾向是：\",options:[{name:\"A\",text:\"职业道德认识 \"},{name:\"B\",text:\"职业道德行为 \"},{name:\"C\",text:\"职业道德品质 \"},{name:\"D\",text:\"职业道德规范\"}]};" +
                " showQuestion(jsonQuestion,2713,\"0\");" +
                "</script>" +
                "            </ul>" +
                "                        <ul class=\"mt30\">" +
                "                <li class=\"ks_t_xh\">27</li>" +
                "                <li class=\"ks_tm mb10\"  id=\"2714\"></li>" +
                "                <div class=\"clear\"></div>" +
                "                <div id=\"option2714\">" +
                "                </div>" +
                "                <script type=\"text/javascript\">" +
                "var jsonQuestion={title:\"以下关于职业道德的说法，正确的是：\",options:[{name:\"A\",text:\"职业道德只讲职责，不讲权利和利益 \"},{name:\"B\",text:\"职业道德只讲职责和权利，不讲利益 \"},{name:\"C\",text:\"职业道德只讲权利，不讲义务 \"},{name:\"D\",text:\"职业道德是责、权、利的统一\"}]};" +
                " showQuestion(jsonQuestion,2714,\"0\");" +
                "</script>" +
                "            </ul>" +
                "                        <ul class=\"mt30\">" +
                "                <li class=\"ks_t_xh\">28</li>" +
                "                <li class=\"ks_tm mb10\"  id=\"2719\"></li>" +
                "                <div class=\"clear\"></div>" +
                "                <div id=\"option2719\">" +
                "                </div>" +
                "                <script type=\"text/javascript\">" +
                "var jsonQuestion={title:\"俗话说：金无足赤，人无完人。在我们的沟通活动中，往往会发现部下的缺点和错误。当我们发现部下错误时，要及时地加以指正和批评，关键要注意方法是否得当。在批评部下时，我们应该注意：\",options:[{name:\"A\",text:\"在批评部下时，如果只提他的短处而不提他的长处，他就会感到心理上的不平衡，感到委屈 \"},{name:\"B\",text:\"批评他人通常是比较严肃的事情，所以在批评的时候一定要客观具体，应该就事论事，要记住，我们批评他人，并不是批评对方本人，而是批评他的错误的行为，千万不要把对部下错误行为的批评扩大到了对部下本人的批评上 \"},{name:\"C\",text:\"每次的批评都应尽量在友好的气氛中结束，这样才能彻底解决问题 \"},{name:\"D\",text:\"以上说法都正确\"}]};" +
                " showQuestion(jsonQuestion,2719,\"0\");" +
                "</script>" +
                "            </ul>" +
                "                        <ul class=\"mt30\">" +
                "                <li class=\"ks_t_xh\">29</li>" +
                "                <li class=\"ks_tm mb10\"  id=\"3208\"></li>" +
                "                <div class=\"clear\"></div>" +
                "                <div id=\"option3208\">" +
                "                </div>" +
                "                <script type=\"text/javascript\">" +
                "var jsonQuestion={title:\"职场成功的第一步是成为职业化的员工。你认为这种说法：\",options:[{name:\"A\",text:\"正确 \"},{name:\"B\",text:\"错误\"}]};" +
                " showQuestion(jsonQuestion,3208,\"0\");" +
                "</script>" +
                "            </ul>" +
                "                        <ul class=\"mt30\">" +
                "                <li class=\"ks_t_xh\">30</li>" +
                "                <li class=\"ks_tm mb10\"  id=\"2689\"></li>" +
                "                <div class=\"clear\"></div>" +
                "                <div id=\"option2689\">" +
                "                </div>" +
                "                <script type=\"text/javascript\">" +
                "var jsonQuestion={title:\"职场中遇到挫折再所难免。那么职场受挫后如何才能防止消极结果的产生呢？以下说法正确的是：\",options:[{name:\"A\",text:\"寻找合适的对象积极的倾诉，即将你的痛苦向你认为值得信赖的人倾诉 \"},{name:\"B\",text:\"认真分析、审视自己的受挫的过程，多从自身找原因，克服工作中自身存在的问题 \"},{name:\"C\",text:\"重新审视自己的职业目标是否合适，设定或调整可行的阶段目标 \"},{name:\"D\",text:\"以上都对\"}]};" +
                " showQuestion(jsonQuestion,2689,\"0\");" +
                "</script>" +
                "            </ul>" +
                "                    " +
                "            <h1 class=\"ks_left_tit mt30\" id=\"zhuguanti\">" +
                "        主观题" +
                "        <span class=\"col666 fs14 ml8\">" +
                "        (共4题,每题10分)" +
                "        </span>" +
                "        </h1>" +
                "                        <ul class=\"mt30\">" +
                "                <li class=\"ks_t_xh\">1</li>" +
                "                <li class=\"ks_tm mb10\" id=\"2807\">签订劳动合同有哪些注意事项？" +
                "</li>" +
                "                <div class=\"clear\"></div>" +
                "                <textarea class=\"area_w625 area_wh ml28\" id=\"sub_2807\" questionid=\"2807\" onkeyup=\"getFinishCount()\" name=\"textarea_sub\"></textarea>" +
                "            </ul>" +
                "                        <ul class=\"mt30\">" +
                "                <li class=\"ks_t_xh\">2</li>" +
                "                <li class=\"ks_tm mb10\" id=\"2844\">职场人的个人品牌成长周期分为几个阶段？各阶段的特点及注意事项有哪些？</li>" +
                "                <div class=\"clear\"></div>" +
                "                <textarea class=\"area_w625 area_wh ml28\" id=\"sub_2844\" questionid=\"2844\" onkeyup=\"getFinishCount()\" name=\"textarea_sub\"></textarea>" +
                "            </ul>" +
                "                        <ul class=\"mt30\">" +
                "                <li class=\"ks_t_xh\">3</li>" +
                "                <li class=\"ks_tm mb10\" id=\"2833\">“敬业精神”包括哪些要素？</li>" +
                "                <div class=\"clear\"></div>" +
                "                <textarea class=\"area_w625 area_wh ml28\" id=\"sub_2833\" questionid=\"2833\" onkeyup=\"getFinishCount()\" name=\"textarea_sub\"></textarea>" +
                "            </ul>" +
                "                        <ul class=\"mt30\">" +
                "                <li class=\"ks_t_xh\">4</li>" +
                "                <li class=\"ks_tm mb10\" id=\"2837\">职场着装的“TPO原则”指什么？</li>" +
                "                <div class=\"clear\"></div>" +
                "                <textarea class=\"area_w625 area_wh ml28\" id=\"sub_2837\" questionid=\"2837\" onkeyup=\"getFinishCount()\" name=\"textarea_sub\"></textarea>" +
                "            </ul>" +
                "                                    " +
                "        </div>" +
                "                <div class=\"ks_right\">" +
                "        <div id=\"div_question_menu\" style=\"position:fixed; background-color:#fff;\">" +
                "        <ul class=\"ks_r_top\">" +
                "            <li class=\"r_top_time\"><span id=\"timer\"></span></li>" +
                "                <li class=\"r_top_sl mt10\">" +
                "                <span class=\"fs24_z\" id=\"nFinishCount\">0</span>" +
                "                <span class=\"fs24_z mr5\">/34</span>题" +
                "                </li>" +
                "            </ul>" +
                "            <dl class=\"ks_r_cen mt20\" style=\"margin-bottom:10px;\">" +
                "                        <dt class=\"r_cen_dl\"><img src=\"../../image/ks_r_ywhite.png\" /><a class=\"r_cen_tit\" href=\"#keguanti\">客观题</a></dt>" +
                "                                    <dd class=\"rr_cen_dd\">" +
                "            <a class=\"rr_cen_xh\" id=\"obj_nav_2538\" name=\"obj_nav\" onclick=\"swithClass('2538')\" href=\"#2538\">1 - 3</a>" +
                "            </dd>" +
                "                                                                                                <dd class=\"rr_cen_dd\">" +
                "            <a class=\"rr_cen_xh\" id=\"obj_nav_2542\" name=\"obj_nav\" onclick=\"swithClass('2542')\" href=\"#2542\">4 - 6</a>" +
                "            </dd>" +
                "                                                                                                <dd class=\"rr_cen_dd\">" +
                "            <a class=\"rr_cen_xh\" id=\"obj_nav_2644\" name=\"obj_nav\" onclick=\"swithClass('2644')\" href=\"#2644\">7 - 9</a>" +
                "            </dd>" +
                "                                                                                                <dd class=\"rr_cen_dd\">" +
                "            <a class=\"rr_cen_xh\" id=\"obj_nav_2635\" name=\"obj_nav\" onclick=\"swithClass('2635')\" href=\"#2635\">10 - 12</a>" +
                "            </dd>" +
                "                                                                                                <dd class=\"rr_cen_dd\">" +
                "            <a class=\"rr_cen_xh\" id=\"obj_nav_2591\" name=\"obj_nav\" onclick=\"swithClass('2591')\" href=\"#2591\">13 - 15</a>" +
                "            </dd>" +
                "                                                                                                <dd class=\"rr_cen_dd\">" +
                "            <a class=\"rr_cen_xh\" id=\"obj_nav_3956\" name=\"obj_nav\" onclick=\"swithClass('3956')\" href=\"#3956\">16 - 18</a>" +
                "            </dd>" +
                "                                                                                                <dd class=\"rr_cen_dd\">" +
                "            <a class=\"rr_cen_xh\" id=\"obj_nav_2608\" name=\"obj_nav\" onclick=\"swithClass('2608')\" href=\"#2608\">19 - 21</a>" +
                "            </dd>" +
                "                                                                                                <dd class=\"rr_cen_dd\">" +
                "            <a class=\"rr_cen_xh\" id=\"obj_nav_2704\" name=\"obj_nav\" onclick=\"swithClass('2704')\" href=\"#2704\">22 - 24</a>" +
                "            </dd>" +
                "                                                                                                <dd class=\"rr_cen_dd\">" +
                "            <a class=\"rr_cen_xh\" id=\"obj_nav_3216\" name=\"obj_nav\" onclick=\"swithClass('3216')\" href=\"#3216\">25 - 27</a>" +
                "            </dd>" +
                "                                                                                                <dd class=\"rr_cen_dd\">" +
                "            <a class=\"rr_cen_xh\" id=\"obj_nav_2719\" name=\"obj_nav\" onclick=\"swithClass('2719')\" href=\"#2719\">28 - 30</a>" +
                "            </dd>" +
                "                                                                                                " +
                "                            <dt class=\"r_cen_dl\"><img src=\"../../image/ks_r_ywhite.png\" /><a class=\"r_cen_tit\" href=\"#zhuguanti\">主观题</a></dt>" +
                "                                            <dd class=\"rr_cen_dd\"><a class=\"rr_cen_xh\" id=\"obj_nav_2807\" name=\"obj_nav\" onclick=\"swithClass('2807')\" href=\"#2807\">1 - 3</a></dd>" +
                "                                                                                                                        <dd class=\"rr_cen_dd\"><a class=\"rr_cen_xh\" id=\"obj_nav_2837\" name=\"obj_nav\" onclick=\"swithClass('2837')\" href=\"#2837\">4 - 6</a></dd>" +
                "                                                            <dt class=\"r_cen_js\"></dt>" +
                "            </dl>" +
                "            <input type=\"button\" class=\"ks_tj_btn mt10\" value=\"提交试卷\" onclick=\"commitPager(1)\" />" +
                "            </div>" +
                "        </div>" +
                "        <div class=\"clear\"></div>" +
                "    </div>" +
                "</div>" +
                "" +
                "" +
                "</body>" +
                "<!-- 主观题考生答案信息提取 start -->" +
                "<script>" +
                "var strQuestionIds1 = \"\";" +
                "if(strQuestionIds1.length>0)" +
                "{" +
                "var strQuestionAnswer1 = ;" +
                "var strScoreDetail1 = \"\";" +
                "//主观题考生答案信息提取" +
                "showAnswer1(strQuestionIds1,strQuestionAnswer1,strScoreDetail1);" +
                "}" +
                "</script>" +
                "<!-- 主观题考生答案信息提取 end -->" +
                "" +
                "<script>" +
                "getRealEndDate();" +
                "</script>" +
                "<script>" +
                "getFinishCount();" +
                "</script>" +
                "<script type=\"text/javascript\" src=\"http://static.njcedu.com/js/WebView.js?v=20181107160944\" ></script>" +
                "</html>";


//        String regex = "title:\"(.*?)\",options:\\[.*?showQuestion\\(jsonQuestion,(\\d{4}),";
//        Pattern pattern = Pattern.compile(regex);
//        Matcher matcher = pattern.matcher(jsonQuestionHtml);
//        while(matcher.find()){
////            System.out.println(matcher.groupCount());
//            //System.out.println("0:"+matcher.group(0)); //最大总集　
//            System.out.println(matcher.group(1)); //(.*?)第一匹配
//            System.out.println(matcher.group(2)); //\d{4})第二匹配
//
//        }
//        System.out.print(matcher.find());
//        System.out.print(matcher.find());
//        System.out.print(matcher.find());
//        System.out.print(matcher.find());
//        System.out.print(matcher.find());
//        matcher.find();
//        String s =  matcher.group(0);
//        System.out.print(s);

    }
}
