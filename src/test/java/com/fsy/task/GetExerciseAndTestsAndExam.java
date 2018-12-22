package com.fsy.task;

import org.htmlparser.tags.TableTag;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;
import java.util.stream.Collectors;

public class GetExerciseAndTestsAndExam {
    public static void main(String[] args) {
        String html = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
                "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                "<head>\n" +
                "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n" +
                "<title>学生端-学生任务主页</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<!-- header -->\n" +
                "\n" +
                "\n" +
                "\n" +
                "<!-- JS  -->\n" +
                "<script type=\"text/javascript\" src=\"http://static.njcedu.com/JSLib/CDOJS/Utility.js?v=20181107160944\"></script>\n" +
                "<script type=\"text/javascript\" src=\"http://static.njcedu.com/JSLib/CDOJS/HashMap.js?v=20181107160944\"></script>\n" +
                "<script type=\"text/javascript\" src=\"http://static.njcedu.com/JSLib/CDOJS/CDO.js?v=20181107160944\"></script>\n" +
                "<script type=\"text/javascript\" src=\"http://static.njcedu.com/JSLib/CDOJS/HttpClient.js?v=20181107160944\"></script>\n" +
                "<script type=\"text/javascript\" src=\"http://static.njcedu.com/JSLib/CDOJS/Validator.js?v=20181107160944\"></script>\n" +
                "<script type=\"text/javascript\" src=\"http://static.njcedu.com/JSLib/CDOJS/ChoiceTree/SingleChoiceBox.js?v=20181107160944\"></script>\n" +
                "<script type=\"text/javascript\" src=\"http://static.njcedu.com/JSLib/Utility.js?v=20181107160944\" ></script>\n" +
                "\n" +
                "<script type=\"text/javascript\" src=\"http://static.njcedu.com/js/cookie.js?v=20181107160944\"></script>\n" +
                "<script type=\"text/javascript\" src=\"http://static.njcedu.com/js/jquery-1.7.2.min.js?v=20181107160944\"></script>\n" +
                "<!-- JS END  -->\n" +
                "\n" +
                "<!-- CSS -->\n" +
                "<link rel=\"stylesheet\" type=\"text/css\" href=\"/student/my/css/basic.css?v=20181107160944\"/>\n" +
                "<link rel=\"stylesheet\" type=\"text/css\" href=\"/student/my/css/personalCenter.css?v=20181107160944\"/>\n" +
                "<link type=\"text/css\" rel=\"stylesheet\" href=\"/student/css/cdep_common.css?v=20181107160944\" />\n" +
                "<link type=\"text/css\" rel=\"stylesheet\" href=\"/student/css/stu_jxjh_style.css?v=20181107160944\" />\n" +
                "<link type=\"text/css\" rel=\"stylesheet\" href=\"/student/css/head.css?v=20181107160944\" />\n" +
                "<!-- CSS END -->\n" +
                "\n" +
                " \n" +
                " \n" +
                " \n" +
                "   \n" +
                "   \n" +
                "   \n" +
                " \n" +
                "<script type=\"text/javascript\">\n" +
                "\tvar getMessageParam = function()\n" +
                "\t{\n" +
                "\t\tvar arr=new Array();\n" +
                "\t\tarr[\"lUserId\"]=19000009364;\n" +
                "\t\tarr[\"nType\"]=1;\n" +
                "\t\tarr[\"lSchoolId\"]=585;\n" +
                "\t\tarr[\"lProvinceId\"]=61;\n" +
                "\t\tarr[\"lDepartmentId\"]=0;\n" +
                "\t\tarr[\"lGradeId\"]=20151;\n" +
                "\t\tarr[\"lClassId\"]=29200000465;\n" +
                "\t\treturn arr;\n" +
                "\t};\n" +
                "\tvar isIE6=false;//用于判断是否为IE6\n" +
                "</script>  \n" +
                "\n" +
                "\n" +
                "\n" +
                "<span id=\"header_div\">  \n" +
                "\n" +
                "<style type=\"text/css\">\n" +
                "<!--\n" +
                ".STYLE1 {color: #FF0000}\n" +
                "-->\n" +
                "</style>\n" +
                "<link rel=\"stylesheet\" href=\"/student/css/student_style.css\" type=\"text/css\"></link>\n" +
                "\n" +
                "<div class=\"s_top\">\n" +
                "<ul>\n" +
                "    <li class=\"s_top_b\">\n" +
                "    \t <a class=\"top_logo\" href=\"http://yau.njcedu.com\" target=\"_blank\">\n" +
                "\t\t  <img style=\"float:left;width: 175px;height: 40px; margin-top:15px;\" src=\"http://file1.njcedu.com/img/201503/f19d16ab04b04053bb8a7c2b6da4c35c.jpg\" /> \n" +
                "\t\t</a>\n" +
                "\t\t\t\t<span class=\"logo_txtb\">职业发展教育平台</span>\n" +
                "\t\t    </li>\n" +
                "    \t<li class=\"s_top_t\">\n" +
                "    \t\t            <div class=\"top_kj\">\n" +
                "            \t<div class=\"kj_btn\" id=\"kj_btn_div\"  onmouseover=\"getDivHover(0,this)\" onmouseout=\"getDivHover(1,this)\">\n" +
                "                \t<input type=\"button\" class=\"top_kj_btn\" /><img src=\"/student/image/top_jt_b.png\" />\n" +
                "                </div>\n" +
                "                <div class=\"kj_tk\" id=\"kj_tkId\" onmouseover=\"getDivHover(0,this)\" onmouseout=\"getDivHover(1,this)\">\n" +
                "                    <ul>\n" +
                "                        <li onclick=\"window.location.replace('http://yau.njcedu.com')\"><span>学校空间</span></li>\n" +
                "                                                                        <li onclick=\"window.location.replace('http://yau.njcedu.com/help/helpIndex.htm')\"><span>帮助中心</span></li>\n" +
                "                        <li onclick=\"window.location='http://www.jincin.com'\"><span>锦成网</span></li>\n" +
                "                    </ul>\n" +
                "                    <div class=\"right_tc_bai\"></div>\n" +
                "                </div>\n" +
                "            </div>\n" +
                "                        <div class=\"top_tz\" id=\"msgContainer\">\n" +
                "        \t\t<div class=\"right_tc_bai\"></div>\n" +
                "            </div>\n" +
                "            <div class=\"top_gr\">\n" +
                "            \t<div class=\"gr_btn\" id=\"gr_btn_div\" onmouseover=\"getDivHover2(0,this)\" onmouseout=\"getDivHover2(1,this)\" >\n" +
                "                \t<input type=\"button\" class=\"top_gr_btn\" /><img src=\"/student/image/top_jt_b.png\" />\n" +
                "                </div>\n" +
                "                <div class=\"gs_tk\" id=\"gs_tkId\" onmouseover=\"getDivHover2(0,this)\" onmouseout=\"getDivHover2(1,this)\" >\n" +
                "                    <ul>\n" +
                "                        <li onclick=\"window.location='/student/my/user/userInfo.htm'\"><span>个人信息</span></li>\n" +
                "                        <li onclick=\"window.location='/student/my/user/password.htm'\"><span>修改密码</span></li>\n" +
                "                        <li onclick=\"window.location='/student/my/user/verifyEmail1.htm'\"><span>邮箱验证</span></li>\n" +
                "                        <li onclick=\"window.location='/student/my/user/verifyMobile.htm'\"><span>手机验证</span></li>\n" +
                "                        <li class=\"fgx\"  onclick=\"window.location.replace('http://sso.njcedu.com/logout.htm?domain=yau.njcedu.com&targeturl=')\"><span>退出</span></li>\n" +
                "                    </ul>\n" +
                "                    <div class=\"right_tc_bai\"></div>\n" +
                "                </div>\n" +
                "            </div>\n" +
                "            <div class=\"s_top_name\">hi,\n" +
                "\t\t\t \t\t        \t赵钰萌\n" +
                "\t\t     \t\t\t</div>\n" +
                "            <div class=\"clear\"></div>\n" +
                "         </li>\n" +
                "         <div class=\"clear\"></div>\n" +
                "  </ul>\n" +
                "</div>\n" +
                "\n" +
                "<script type=\"text/javascript\">\n" +
                "function getDivHover2(nFlag,obj){\n" +
                "\tif(nFlag == 0){\n" +
                "\t\tvar parent = obj.parentNode;\n" +
                "\t\tvar div= parent.childNodes;\n" +
                "\t\tvar div0 = div[0];\n" +
                "\t\tvar div1 = div[1];\n" +
                "\t\tdiv0.className = \"gr_btn_hover\";\n" +
                "\t\tif(isIE6){\n" +
                "\t\t\tdiv1.className = \"gs_tk_ie6\";\n" +
                "\t\t}\n" +
                "\t}\n" +
                "\tif(nFlag == 1){\n" +
                "\t\tvar parent = obj.parentNode;\n" +
                "\t\tvar div= parent.childNodes;\n" +
                "\t\tvar div0 = div[0];\n" +
                "\t\tvar div1 = div[1];\n" +
                "\t\tdiv0.className = \"gr_btn\";\n" +
                "\t\tif(isIE6){\n" +
                "\t\t\tdiv1.className = \"gs_tk\";\n" +
                "\t\t}\n" +
                "\t\t\n" +
                "\t}\n" +
                "}\n" +
                "\n" +
                "function getDivHover(nFlag,obj){\n" +
                "\tif(nFlag == 0){\n" +
                "\t\tvar parent = obj.parentNode;\n" +
                "\t\tvar div= parent.childNodes;\n" +
                "\t\tvar div0 = div[0];\n" +
                "\t\tvar div1 = div[1];\n" +
                "\t\tdiv0.className = \"kj_btn_hover\";\n" +
                "\t\tif(isIE6){\n" +
                "\t\t\tdiv1.className = \"kj_tk_ie6\";\n" +
                "\t\t}\n" +
                "\t}\n" +
                "\tif(nFlag == 1){\n" +
                "\t\tvar parent = obj.parentNode;\n" +
                "\t\tvar div= parent.childNodes;\n" +
                "\t\tvar div0 = div[0];\n" +
                "\t\tvar div1 = div[1];\n" +
                "\t\tdiv0.className = \"kj_btn\";\n" +
                "\t\tif(isIE6){\n" +
                "\t\t\tdiv1.className = \"kj_tk\";\n" +
                "\t\t}\n" +
                "\t\t\n" +
                "\t}\n" +
                "}\n" +
                "</script>\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "<!---------header部分---------></span>\n" +
                "<span id=\"menu_div\"></span>\n" +
                "<div class=\"s_nav\">\n" +
                "\t<ul class=\"w1200\" id=\"menuList\">\n" +
                "\t\t<li class=\"nav_li_ding\" id=\"menuLiIndex\" onclick=\"location.href='http://yau.njcedu.com/student/my/welcome/welcome.htm'\">\n" +
                "\t\t\t<span>首页</span>\n" +
                "\t\t\t<a name=\"menuA\" key=\"/my/welcome/\" parentId=\"menuLiIndex\" style=\"display:none;\"></a>\n" +
                "\t\t</li>\n" +
                "\t\t\t\t\t\t<li class=\"nav_li\" id=\"menuLiTeachActivity\" >\n" +
                "\t\t\t\t\t\t<span >我的任务</span>\n" +
                "\t\t\t\t\t\t\t\t\t\n" +
                "\t\t\t\t\t\t<input type=\"button\" class=\"nav_btn\" id=\"menuLiTeachActivity_input\" />\n" +
                "\t\t\t    \t\t<ul class=\"nav_tc\">\n" +
                "    \t\t\t    \t\t\t    \t\t\t<li class=\"nav_tc_li\"  onclick=\"location.href='/student/prese/teachplan/index.htm'\" >\n" +
                "\t\t\t\t\t<a name=\"menuA\" currentPosition=\"我的任务 > 学习任务\" \n" +
                "\t\t\t\t\t key=\"/teachplan/\" \t\t\t\t\tparentId=\"menuLiTeachActivity\">\n" +
                "\t\t\t\t\t\t学习任务\n" +
                "\t\t\t\t\t</a>\n" +
                "\t\t\t\t</li>\n" +
                "\t\t\t\t    \t\t\t    \t\t\t    \t\t\t<li class=\"nav_tc_li\"  onclick=\"location.href='/student/prese/examin/list.htm'\" >\n" +
                "\t\t\t\t\t<a name=\"menuA\" currentPosition=\"我的任务 > 考试\" \n" +
                "\t\t\t\t\t key=\"/examin/\" \t\t\t\t\tparentId=\"menuLiTeachActivity\">\n" +
                "\t\t\t\t\t\t考试\n" +
                "\t\t\t\t\t</a>\n" +
                "\t\t\t\t</li>\n" +
                "\t\t\t\t    \t\t\t    \t\t\t    \t\t\t<li class=\"nav_tc_li\"  onclick=\"location.href='/student/prese/homework/list.htm'\" >\n" +
                "\t\t\t\t\t<a name=\"menuA\" currentPosition=\"我的任务 > 作业\" \n" +
                "\t\t\t\t\t key=\"/homework/\" \t\t\t\t\tparentId=\"menuLiTeachActivity\">\n" +
                "\t\t\t\t\t\t作业\n" +
                "\t\t\t\t\t</a>\n" +
                "\t\t\t\t</li>\n" +
                "\t\t\t\t    \t\t\t    \t\t</ul>\n" +
                "\t\t\t\t\t</li>\n" +
                "\t\t\t\t\t\t\t\t<li class=\"nav_li\" id=\"menuLiCourse\" >\n" +
                "\t\t\t\t\t\t<span  onclick=\"location.href='/student/course/opencourse.htm'\" >在线课程</span>\n" +
                "\t\t\t\t\t\t    \t\t<a name=\"menuA\" currentPosition=\"在线课程\" key=\"/student/course/\" parentId=\"menuLiCourse\" style=\"display:none;\"></a>\n" +
                "    \t\t\t\t\t\n" +
                "\t\t\t\t\t</li>\n" +
                "\t\t\t\t\t\t\t\t<li class=\"nav_li\" id=\"menuLiCareerPlan\" >\n" +
                "\t\t\t\t\t\t<span >生涯规划</span>\n" +
                "\t\t\t\t\t\t\t\t\t\n" +
                "\t\t\t\t\t\t<input type=\"button\" class=\"nav_btn\" id=\"menuLiCareerPlan_input\" />\n" +
                "\t\t\t    \t\t<ul class=\"nav_tc\">\n" +
                "    \t\t\t    \t\t\t    \t\t\t<li class=\"nav_tc_li\"  onclick=\"location.href='/student/tc/careerPlaning/careerMyself.htm'\" >\n" +
                "\t\t\t\t\t<a name=\"menuA\" currentPosition=\"生涯规划 > 发现职业自我\" \n" +
                "\t\t\t\t\t key=\"/student/tc/careerPlaning/\" \t\t\t\t\tparentId=\"menuLiCareerPlan\">\n" +
                "\t\t\t\t\t\t发现职业自我\n" +
                "\t\t\t\t\t</a>\n" +
                "\t\t\t\t</li>\n" +
                "\t\t\t\t    \t\t\t    \t\t\t    \t\t\t<li class=\"nav_tc_li\"  onclick=\"location.href='/student/tc/careerPlaning/careerRealWorld.htm'\" >\n" +
                "\t\t\t\t\t<a name=\"menuA\" currentPosition=\"生涯规划 > 探索职业世界\" \n" +
                "\t\t\t\t\t key=\"/student/tc/careerPlaning/\" \t\t\t\t\tparentId=\"menuLiCareerPlan\">\n" +
                "\t\t\t\t\t\t探索职业世界\n" +
                "\t\t\t\t\t</a>\n" +
                "\t\t\t\t</li>\n" +
                "\t\t\t\t    \t\t\t    \t\t\t    \t\t\t<li class=\"nav_tc_li\"  onclick=\"location.href='/student/tc/careerPlaning/careerClearGoal.htm'\" >\n" +
                "\t\t\t\t\t<a name=\"menuA\" currentPosition=\"生涯规划 > 明晰职业目标\" \n" +
                "\t\t\t\t\t key=\"/student/tc/careerPlaning/\" \t\t\t\t\tparentId=\"menuLiCareerPlan\">\n" +
                "\t\t\t\t\t\t明晰职业目标\n" +
                "\t\t\t\t\t</a>\n" +
                "\t\t\t\t</li>\n" +
                "\t\t\t\t    \t\t\t    \t\t\t    \t\t\t<li class=\"nav_tc_li\"  onclick=\"location.href='/student/tc/actionPlan/list.htm'\" >\n" +
                "\t\t\t\t\t<a name=\"menuA\" currentPosition=\"生涯规划 > 制定行动计划\" \n" +
                "\t\t\t\t\t key=\"/student/tc/actionPlan/\" \t\t\t\t\tparentId=\"menuLiCareerPlan\">\n" +
                "\t\t\t\t\t\t制定行动计划\n" +
                "\t\t\t\t\t</a>\n" +
                "\t\t\t\t</li>\n" +
                "\t\t\t\t    \t\t\t    \t\t\t    \t\t\t<li class=\"nav_tc_li\"  onclick=\"location.href='/student/tc/careerPlaning/careerGrowthTest.htm'\" >\n" +
                "\t\t\t\t\t<a name=\"menuA\" currentPosition=\"生涯规划 > 生涯成长检测\" \n" +
                "\t\t\t\t\t key=\"/student/tc/careerPlaning/\" \t\t\t\t\tparentId=\"menuLiCareerPlan\">\n" +
                "\t\t\t\t\t\t生涯成长检测\n" +
                "\t\t\t\t\t</a>\n" +
                "\t\t\t\t</li>\n" +
                "\t\t\t\t    \t\t\t    \t\t\t    \t\t\t<li class=\"nav_tc_li\"  onclick=\"location.href='/student/tc/careerPlaning/record.htm'\" >\n" +
                "\t\t\t\t\t<a name=\"menuA\" currentPosition=\"生涯规划 > 生涯成长档案\" \n" +
                "\t\t\t\t\t key=\"/student/prese/homework/\" \t\t\t\t\tparentId=\"menuLiCareerPlan\">\n" +
                "\t\t\t\t\t\t生涯成长档案\n" +
                "\t\t\t\t\t</a>\n" +
                "\t\t\t\t</li>\n" +
                "\t\t\t\t    \t\t\t    \t\t\t    \t\t\t<li class=\"nav_tc_li\"  onclick=\"location.href='/student/prese/careerPlan/list.htm'\" >\n" +
                "\t\t\t\t\t<a name=\"menuA\" currentPosition=\"生涯规划 > 生涯规划书\" \n" +
                "\t\t\t\t\t key=\"/student/prese/homework/\" \t\t\t\t\tparentId=\"menuLiCareerPlan\">\n" +
                "\t\t\t\t\t\t生涯规划书\n" +
                "\t\t\t\t\t</a>\n" +
                "\t\t\t\t</li>\n" +
                "\t\t\t\t    \t\t\t    \t\t</ul>\n" +
                "\t\t\t\t\t</li>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<li class=\"nav_li\" id=\"menuLiConsultation\" >\n" +
                "\t\t\t\t\t\t<span  onclick=\"location.href='/student/consultation/schedule.htm'\" >咨询预约</span>\n" +
                "\t\t\t\t\t\t    \t\t<a name=\"menuA\" currentPosition=\"咨询预约\" key=\"/student/consultation/\" parentId=\"menuLiConsultation\" style=\"display:none;\"></a>\n" +
                "    \t\t\t\t\t\n" +
                "\t\t\t\t\t</li>\n" +
                "\t\t\t\t\t</ul>\n" +
                "</div>\n" +
                "\n" +
                "  \n" +
                "  <!-- header end -->\n" +
                "\n" +
                "<script type=\"text/javascript\" src=\"http://static.njcedu.com/js/amcharts/amcharts.js?v=20181107160944\" ></script>\n" +
                "<script type=\"text/javascript\" src=\"http://static.njcedu.com/js/amcharts/pie.js?v=20181107160944\" ></script>\n" +
                "<script type=\"text/javascript\" src=\"listdetail.js?v=20181107160944\" ></script>\n" +
                "\n" +
                " <!--当前位置-->\n" +
                "<div class=\"dqwz\">当前位置： 学习管理 > <a href=\"list.htm\">学习任务</a> > 2015级学生职业发展与就业指导课学习任务（五）</div>\n" +
                "  \t \t\t \t\t \t \t \t   \t  \t<input id=\"nPlanState\" value=\"0\" type=\"hidden\"/>\n" +
                "\t<input id=\"bScoreState\" value=\"false\" type=\"hidden\"/>\n" +
                "\t<input id=\"totledata\" value=\"2\" type=\"hidden\"/>\n" +
                "\t<input type=\"hidden\" id=\"strName\" value=\"2015级学生职业发展与就业指导课学习任务（五）\"/>\n" +
                "\t<div class=\"content\">\n" +
                "\t\t\t\t<img class=\"fl\" src=\"http://static.njcedu.com/images/ewm_small.png\"/>\n" +
                "\t\t\t\t<div class=\"stu_ks fl ml20\">\n" +
                "\t\t\t<h1 class=\"stu_ks_tit\">2015级学生职业发展与就业指导课学习任务（五）</h1>\n" +
                "\t\t    <p class=\"col999 ta_cen\">2018-10-08 至  2018-12-31</p>\n" +
                "\t\t</div>\n" +
                "\t\t<div class=\"fr\">\n" +
                "\t\t\t\t\t</div>\n" +
                "\t\t<div class=\"clear\"></div>\n" +
                "\t\t<div class=\"js_fs mt10\" >\n" +
                "    \t<ul class=\"js_l fl\"   style=\"display:none;\">\n" +
                "    \t\t<div>\n" +
                "\t        \t<p class=\"col666 fs24_z mt30\">该任务已于<span class=\"col_red mlr5\">2018-12-31</span>结束</p>\n" +
                "\t    \t\t<p class=\"col666 fs24_z mt10\">学习概览</p>\n" +
                "    \t\t</div>\n" +
                "        </ul>\n" +
                "                <div class=\"clear\"></div>\n" +
                "</div>\n" +
                "\t</div>\n" +
                "<!--t_con-->\n" +
                "\t<input type=\"hidden\" id=\"lSchoolId\" value=\"585\" />\n" +
                "\t<input type=\"hidden\" id=\"lUserId\" value=\"19000009364\" />\n" +
                "\n" +
                "\t<input type=\"hidden\" id=\"lSchoolId\" value=\"585\"/> \n" +
                "\t<input type=\"hidden\" id=\"lUserId\" value=\"19000009364\"/> \n" +
                "\t<input type=\"hidden\" id=\"courseCenterURL\" value=\"http://course.njcedu.com\"/> \n" +
                "\t<div class=\"xk_con\">\n" +
                "\t\t<ul class=\"col999 fs30\" style=\"width:990px; height:300px; margin:30px auto;display:none;\" id=\"wordwarn\" >\n" +
                "\t\t\t<p style=\"text-align:center; padding-top:30px;\">老师暂未安排学习内容</p>\n" +
                "\t\t</ul>\n" +
                "\t    <div class=\"content\" id=\"contentshow\" >\n" +
                "\t    \t<ul class=\"ks_zy_left pt20\" id=\"menudiv\">\n" +
                "\t\t\t\t\t        \t\t<li class=\"zy_l\"  id=\"li1\" onclick=\"showLocation(1);\" >课程</li>\n" +
                "\t        \t\t        \t\t            \t<li class=\"zy_l\"  id=\"li2\" onclick=\"showLocation(2);\">测评</li>\n" +
                "\t            \t            <li  class=\"zy_l\" id=\"li3\" onclick=\"showLocation(3);\" style=\"display:none;\">考试</li>\n" +
                "\t            <li class=\"zy_l\" id=\"li4\" onclick=\"showLocation(4);\" style=\"display:none;\">作业</li>\n" +
                "\t            \t            \t        </ul>\n" +
                "\t        <ul class=\"ks_zy_right pt20\" id=\"showCourse\">\n" +
                "\t        \t\t       \t\t\t\t       \t<div class=\"zy_r_fl mb30\" id=\"div1\" name=\"data\" index=\"1\">\n" +
                "              <p class=\"zy_r_tit_xz\">课程</p>\n" +
                "              <p class=\"zy_r_xx\">请于<span class=\"col_red mlr5\">2018-12-31</span>前看完以下课程</p>\n" +
                "              <table class=\"tab_820 jl_table\" cellpadding=\"0\" cellspacing=\"0\" id=\"courseTable\">\n" +
                "                  <tr height=\"36\" class=\"tr_bg\">\n" +
                "                      <td width=\"370\" style=\"padding-left:10px; color:#666;\">名称</td>\n" +
                "                    <td width=\"300\" style=\"padding-left:10px; color:#666;\">状态（已观看/总时长）</td>\n" +
                "                    <td width=\"200\" style=\"padding-left:10px; color:#666;\">课后习题正确率</td>\n" +
                "                    <td width=\"150\" style=\"padding-left:10px; color:#666;\">操作</td>\n" +
                "                </tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t                 \t <tr height=\"50\">\n" +
                "\t\t\t                       <td style=\"padding-left:10px; color:#333;\">从学生到职业人的过渡（一）</td>\n" +
                "\t\t\t                       \t\t\t                       \t\t\t\t\t\t\t\t\t<td style=\"padding-left:10px; color:#888;\">已完成（45 / 45）</td>\n" +
                "\t\t\t\t\t\t\t\t\t<td style=\"padding-left:10px; color:#888;\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t100.0%\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t<td style=\"padding-left:10px;\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t                        \t\t<a class=\"a_color\" href=\"javaScript:show(1678);\">重看</a>&nbsp;<a class=\"a_color\" href=\"javaScript:showExercise(1678);\">课后习题</a>\n" +
                "\t\t\t                        \t\t\t\t                        </td>\n" +
                "\t\t\t\t\t\t\t\t\t\n" +
                "\t\t\t\t\t\t\t\t\t\t\t               </tr>\n" +
                "\t\t\t          \t\t         \t\t\t\t\t\t\t\t\t                 \t <tr height=\"50\">\n" +
                "\t\t\t                       <td style=\"padding-left:10px; color:#333;\">从学生到职业人的过渡（二）</td>\n" +
                "\t\t\t                       \t\t\t                       \t\t\t\t\t\t\t\t\t<td style=\"padding-left:10px; color:#888;\">已完成（51 / 51）</td>\n" +
                "\t\t\t\t\t\t\t\t\t<td style=\"padding-left:10px; color:#888;\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t100.0%\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t<td style=\"padding-left:10px;\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t                        \t\t<a class=\"a_color\" href=\"javaScript:show(1679);\">重看</a>&nbsp;<a class=\"a_color\" href=\"javaScript:showExercise(1679);\">课后习题</a>\n" +
                "\t\t\t                        \t\t\t\t                        </td>\n" +
                "\t\t\t\t\t\t\t\t\t\n" +
                "\t\t\t\t\t\t\t\t\t\t\t               </tr>\n" +
                "\t\t\t          \t\t         \t\t\t\t\t\t\t\t\t                 \t <tr height=\"50\">\n" +
                "\t\t\t                       <td style=\"padding-left:10px; color:#333;\">从学生到职业人的过渡（三）</td>\n" +
                "\t\t\t                       \t\t\t                       \t\t\t\t\t\t\t\t\t<td style=\"padding-left:10px; color:#888;\">已完成（52 / 52）</td>\n" +
                "\t\t\t\t\t\t\t\t\t<td style=\"padding-left:10px; color:#888;\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t100.0%\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t<td style=\"padding-left:10px;\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t                        \t\t<a class=\"a_color\" href=\"javaScript:show(1680);\">重看</a>&nbsp;<a class=\"a_color\" href=\"javaScript:showExercise(1680);\">课后习题</a>\n" +
                "\t\t\t                        \t\t\t\t                        </td>\n" +
                "\t\t\t\t\t\t\t\t\t\n" +
                "\t\t\t\t\t\t\t\t\t\t\t               </tr>\n" +
                "\t\t\t          \t\t         \t\t\t\t\t\t\t\t\t                 \t <tr height=\"50\">\n" +
                "\t\t\t                       <td style=\"padding-left:10px; color:#333;\">工作中应注意的因素（一）</td>\n" +
                "\t\t\t                       \t\t\t                       \t\t\t\t\t\t\t\t\t<td style=\"padding-left:10px; color:#888;\">已完成（52 / 52）</td>\n" +
                "\t\t\t\t\t\t\t\t\t<td style=\"padding-left:10px; color:#888;\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t100.0%\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t<td style=\"padding-left:10px;\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t                        \t\t<a class=\"a_color\" href=\"javaScript:show(1291);\">重看</a>&nbsp;<a class=\"a_color\" href=\"javaScript:showExercise(1291);\">课后习题</a>\n" +
                "\t\t\t                        \t\t\t\t                        </td>\n" +
                "\t\t\t\t\t\t\t\t\t\n" +
                "\t\t\t\t\t\t\t\t\t\t\t               </tr>\n" +
                "\t\t\t          \t\t         \t\t\t\t\t\t\t\t\t                 \t <tr height=\"50\">\n" +
                "\t\t\t                       <td style=\"padding-left:10px; color:#333;\">工作中应注意的因素（二）</td>\n" +
                "\t\t\t                       \t\t\t                       \t\t\t\t\t\t\t\t\t<td style=\"padding-left:10px; color:#888;\">已完成（50 / 50）</td>\n" +
                "\t\t\t\t\t\t\t\t\t<td style=\"padding-left:10px; color:#888;\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t100.0%\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t<td style=\"padding-left:10px;\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t                        \t\t<a class=\"a_color\" href=\"javaScript:show(1292);\">重看</a>&nbsp;<a class=\"a_color\" href=\"javaScript:showExercise(1292);\">课后习题</a>\n" +
                "\t\t\t                        \t\t\t\t                        </td>\n" +
                "\t\t\t\t\t\t\t\t\t\n" +
                "\t\t\t\t\t\t\t\t\t\t\t               </tr>\n" +
                "\t\t\t          \t\t         \t\t\t\t\t\t\t\t\t                 \t <tr height=\"50\">\n" +
                "\t\t\t                       <td style=\"padding-left:10px; color:#333;\">工作中应注意的因素（三）</td>\n" +
                "\t\t\t                       \t\t\t                       \t\t\t\t\t\t\t\t\t<td style=\"padding-left:10px; color:#888;\">已完成（50 / 50）</td>\n" +
                "\t\t\t\t\t\t\t\t\t<td style=\"padding-left:10px; color:#888;\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t100.0%\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t<td style=\"padding-left:10px;\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t                        \t\t<a class=\"a_color\" href=\"javaScript:show(1294);\">重看</a>&nbsp;<a class=\"a_color\" href=\"javaScript:showExercise(1294);\">课后习题</a>\n" +
                "\t\t\t                        \t\t\t\t                        </td>\n" +
                "\t\t\t\t\t\t\t\t\t\n" +
                "\t\t\t\t\t\t\t\t\t\t\t               </tr>\n" +
                "\t\t\t          \t\t         \t\t\t\t\t\t\t\t\t                 \t <tr height=\"50\">\n" +
                "\t\t\t                       <td style=\"padding-left:10px; color:#333;\">实习是不是真的有用</td>\n" +
                "\t\t\t                       \t\t\t                       \t\t\t\t\t\t\t\t\t<td style=\"padding-left:10px; color:#888;\">已完成（11 / 11）</td>\n" +
                "\t\t\t\t\t\t\t\t\t<td style=\"padding-left:10px; color:#888;\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t-/-\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t<td style=\"padding-left:10px;\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t                        \t\t<a class=\"a_color\" href=\"javaScript:show(1462);\">重看</a>&nbsp;<a class=\"a_color\" href=\"javaScript:showExercise(1462);\">课后习题</a>\n" +
                "\t\t\t                        \t\t\t\t                        </td>\n" +
                "\t\t\t\t\t\t\t\t\t\n" +
                "\t\t\t\t\t\t\t\t\t\t\t               </tr>\n" +
                "\t\t\t          \t\t         \t\t\t\t\t\t\t\t\t                 \t <tr height=\"50\">\n" +
                "\t\t\t                       <td style=\"padding-left:10px; color:#333;\">先就业还是先择业</td>\n" +
                "\t\t\t                       \t\t\t                       \t\t\t\t\t\t\t\t\t<td style=\"padding-left:10px; color:#888;\">已完成（10 / 10）</td>\n" +
                "\t\t\t\t\t\t\t\t\t<td style=\"padding-left:10px; color:#888;\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t-/-\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t<td style=\"padding-left:10px;\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t                        \t\t<a class=\"a_color\" href=\"javaScript:show(1466);\">重看</a>&nbsp;<a class=\"a_color\" href=\"javaScript:showExercise(1466);\">课后习\u0098</a>\n" +
                "\t\t\t                        \t\t\t\t                        </td>\n" +
                "\t\t\t\t\t\t\t\t\t\n" +
                "\t\t\t\t\t\t\t\t\t\t\t               </tr>\n" +
                "\t\t\t          \t\t         \t\t\t\t\t\t\t\t\t                 \t <tr height=\"50\">\n" +
                "\t\t\t                       <td style=\"padding-left:10px; color:#333;\">å¦\u0082ä½\u0095åº\u0094å¯¹æ±\u0082è\u0081\u008Cç\u0084¦è\u0099\u0091</td>\n" +
                "\t\t\t                       \t\t\t                       \t\t\t\t\t\t\t\t\t<td style=\"padding-left:10px; color:#888;\">å·²å®\u008Cæ\u0088\u0090ï¼\u008810 / 10ï¼\u0089</td>\n" +
                "\t\t\t\t\t\t\t\t\t<td style=\"padding-left:10px; color:#888;\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t-/-\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t<td style=\"padding-left:10px;\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t                        \t\t<a class=\"a_color\" href=\"javaScript:show(10001280);\">é\u0087\u008Dç\u009C\u008B</a>&nbsp;<a class=\"a_color\" href=\"javaScript:showExercise(10001280);\">è¯¾å\u0090\u008Eä¹ é¢\u0098</a>\n" +
                "\t\t\t                        \t\t\t\t                        </td>\n" +
                "\t\t\t\t\t\t\t\t\t\n" +
                "\t\t\t\t\t\t\t\t\t\t\t               </tr>\n" +
                "\t\t\t          \t\t         \t\t\t\t\t\t\t\t\t                 \t <tr height=\"50\">\n" +
                "\t\t\t                       <td style=\"padding-left:10px; color:#333;\">å·¥ä½\u009Cç¬¬ä¸\u0080å¹´</td>\n" +
                "\t\t\t                       \t\t\t                       \t\t\t\t\t\t\t\t\t<td style=\"padding-left:10px; color:#888;\">å·²å®\u008Cæ\u0088\u0090ï¼\u008811 / 11ï¼\u0089</td>\n" +
                "\t\t\t\t\t\t\t\t\t<td style=\"padding-left:10px; color:#888;\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t-/-\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t<td style=\"padding-left:10px;\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t                        \t\t<a class=\"a_color\" href=\"javaScript:show(10001282);\">é\u0087\u008Dç\u009C\u008B</a>&nbsp;<a class=\"a_color\" href=\"javaScript:showExercise(10001282);\">è¯¾å\u0090\u008Eä¹ é¢\u0098</a>\n" +
                "\t\t\t                        \t\t\t\t                        </td>\n" +
                "\t\t\t\t\t\t\t\t\t\n" +
                "\t\t\t\t\t\t\t\t\t\t\t               </tr>\n" +
                "\t\t\t          \t\t         \t\t\t\t\t\t\t\t\t                 \t <tr height=\"50\">\n" +
                "\t\t\t                       <td style=\"padding-left:10px; color:#333;\">å¼\u0082å\u009C°æ±\u0082è\u0081\u008Cå¦\u0082ä½\u0095æ\u008F\u0090é«\u0098æ\u0088\u0090å\u008A\u009Fç\u008E\u0087</td>\n" +
                "\t\t\t                       \t\t\t                       \t\t\t\t\t\t\t\t\t<td style=\"padding-left:10px; color:#888;\">å·²å®\u008Cæ\u0088\u0090ï¼\u008812 / 12ï¼\u0089</td>\n" +
                "\t\t\t\t\t\t\t\t\t<td style=\"padding-left:10px; color:#888;\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t-/-\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t<td style=\"padding-left:10px;\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t                        \t\t<a class=\"a_color\" href=\"javaScript:show(10001284);\">é\u0087\u008Dç\u009C\u008B</a>&nbsp;<a class=\"a_color\" href=\"javaScript:showExercise(10001284);\">è¯¾å\u0090\u008Eä¹ é¢\u0098</a>\n" +
                "\t\t\t                        \t\t\t\t                        </td>\n" +
                "\t\t\t\t\t\t\t\t\t\n" +
                "\t\t\t\t\t\t\t\t\t\t\t               </tr>\n" +
                "\t\t\t          \t\t         \t\t\t\t\t\t\t\t\t                 \t <tr height=\"50\">\n" +
                "\t\t\t                       <td style=\"padding-left:10px; color:#333;\">ä¿¡æ\u0081¯å\u0086³å®\u009Aå\u0091½è¿\u0090</td>\n" +
                "\t\t\t                       \t\t\t                       \t\t\t\t\t\t\t\t\t<td style=\"padding-left:10px; color:#888;\">å·²å®\u008Cæ\u0088\u0090ï¼\u008813 / 13ï¼\u0089</td>\n" +
                "\t\t\t\t\t\t\t\t\t<td style=\"padding-left:10px; color:#888;\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t-/-\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t<td style=\"padding-left:10px;\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t                        \t\t<a class=\"a_color\" href=\"javaScript:show(1460);\">é\u0087\u008Dç\u009C\u008B</a>&nbsp;<a class=\"a_color\" href=\"javaScript:showExercise(1460);\">è¯¾å\u0090\u008Eä¹ é¢\u0098</a>\n" +
                "\t\t\t                        \t\t\t\t                        </td>\n" +
                "\t\t\t\t\t\t\t\t\t\n" +
                "\t\t\t\t\t\t\t\t\t\t\t               </tr>\n" +
                "\t\t\t          \t\t         \t\t\t\t\t\t\t\t\t                 \t <tr height=\"50\">\n" +
                "\t\t\t                       <td style=\"padding-left:10px; color:#333;\">å\u0085¬å\u008A¡å\u0091\u0098ç\u0083\u00ADæ\u008E¢ç©¶</td>\n" +
                "\t\t\t                       \t\t\t                       \t\t\t\t\t\t\t\t\t<td style=\"padding-left:10px; color:#888;\">å·²å®\u008Cæ\u0088\u0090ï¼\u008811 / 11ï¼\u0089</td>\n" +
                "\t\t\t\t\t\t\t\t\t<td style=\"padding-left:10px; color:#888;\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t-/-\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t<td style=\"padding-left:10px;\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t                        \t\t<a class=\"a_color\" href=\"javaScript:show(1464);\">é\u0087\u008Dç\u009C\u008B</a>&nbsp;<a class=\"a_color\" href=\"javaScript:showExercise(1464);\">è¯¾å\u0090\u008Eä¹ é¢\u0098</a>\n" +
                "\t\t\t                        \t\t\t\t                        </td>\n" +
                "\t\t\t\t\t\t\t\t\t\n" +
                "\t\t\t\t\t\t\t\t\t\t\t               </tr>\n" +
                "\t\t\t          \t\t         \t\t\t\t\t\t\t\t\t                 \t <tr height=\"50\">\n" +
                "\t\t\t                       <td style=\"padding-left:10px; color:#333;\">å°±ä¸\u009Aæ\u009D\u0083ç\u009B\u008Aä¿\u009Dæ\u008A¤ï¼\u0088ä¸\u0080ï¼\u0089</td>\n" +
                "\t\t\t                       \t\t\t                       \t\t\t\t\t\t\t\t\t<td style=\"padding-left:10px; color:#888;\">å·²å®\u008Cæ\u0088\u0090ï¼\u008843 / 43ï¼\u0089</td>\n" +
                "\t\t\t\t\t\t\t\t\t<td style=\"padding-left:10px; color:#888;\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t100.0%\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t<td style=\"padding-left:10px;\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t                        \t\t<a class=\"a_color\" href=\"javaScript:show(1300);\">é\u0087\u008Dç\u009C\u008B</a>&nbsp;<a class=\"a_color\" href=\"javaScript:showExercise(1300);\">è¯¾å\u0090\u008Eä¹ é¢\u0098</a>\n" +
                "\t\t\t                        \t\t\t\t                        </td>\n" +
                "\t\t\t\t\t\t\t\t\t\n" +
                "\t\t\t\t\t\t\t\t\t\t\t               </tr>\n" +
                "\t\t\t          \t\t         \t\t\t\t\t\t\t\t\t                 \t <tr height=\"50\">\n" +
                "\t\t\t                       <td style=\"padding-left:10px; color:#333;\">å°±ä¸\u009Aæ\u009D\u0083ç\u009B\u008Aä¿\u009Dæ\u008A¤ï¼\u0088äº\u008Cï¼\u0089</td>\n" +
                "\t\t\t                       \t\t\t                       \t\t\t\t\t\t\t\t\t<td style=\"padding-left:10px; color:#888;\">å·²å®\u008Cæ\u0088\u0090ï¼\u008841 / 41ï¼\u0089</td>\n" +
                "\t\t\t\t\t\t\t\t\t<td style=\"padding-left:10px; color:#888;\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t100.0%\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t<td style=\"padding-left:10px;\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t                        \t\t<a class=\"a_color\" href=\"javaScript:show(1302);\">é\u0087\u008Dç\u009C\u008B</a>&nbsp;<a class=\"a_color\" href=\"javaScript:showExercise(1302);\">è¯¾å\u0090\u008Eä¹ é¢\u0098</a>\n" +
                "\t\t\t                        \t\t\t\t                        </td>\n" +
                "\t\t\t\t\t\t\t\t\t\n" +
                "\t\t\t\t\t\t\t\t\t\t\t               </tr>\n" +
                "\t\t\t          \t\t                          </table>\n" +
                "   </div>\n" +
                "        <input type=\"hidden\" id=\"unfinish\" value=\"0\">\n" +
                "   <input type=\"hidden\" id=\"nfinish\" value=\"462\">\n" +
                "   \t<script type=\"text/javascript\">\n" +
                "   \tif(\"0\"!=0 || \"462\"!=0)\n" +
                "   \t{\n" +
                "   \t\t$(\"#li1\").css(\"display\",\"\");\n" +
                "   \t\tvar unfinishCourse=parseInt(\"15\");\n" +
                "   \t\tif(unfinishCourse!=0)\n" +
                "  \t\t{\n" +
                "  \t\t\tif(unfinishCourse > 99)\n" +
                "  \t\t\t{\n" +
                "  \t\t\t\t$(\"#li1\").text(\"è¯¾ç¨\u008Bï¼\u0088Nï¼\u0089\");\n" +
                "  \t\t\t}\n" +
                "  \t\t\telse\n" +
                "  \t\t\t{\n" +
                "  \t\t\t\t$(\"#li1\").text(\"è¯¾ç¨\u008Bï¼\u0088\"+15+\"ï¼\u0089\");\n" +
                "  \t\t\t}\n" +
                "  \t\t}\n" +
                "  \t\tvar myPlanState=\"0\";\n" +
                "\t\tif(myPlanState==1)\n" +
                "\t\t{\n" +
                "\t\t\tpie('unfinish','nfinish','chartdiv_course','coursefinish','courseunfinish');\n" +
                "\t\t}\n" +
                "   \t}\n" +
                "   \t\n" +
                "\t</script>\n" +
                "\t       \t\t\t       \t\t\t       \t\t\t \t\t\t <div class=\"zy_r_fl mb30\" id=\"div2\" name=\"data\" index=\"2\">\n" +
                "\t\t     <p class=\"zy_r_tit\">æµ\u008Bè¯\u0084</p>\n" +
                "\t\t     <p class=\"zy_r_xx\">è¯·äº\u008E<span class=\"col_red mlr5\">2018-12-31</span>å\u0089\u008Dç\u009C\u008Bå®\u008Cä»¥ä¸\u008Bæµ\u008Bè¯\u0084</p>\n" +
                "\t\t     <table class=\"tab_820 jl_table\" cellpadding=\"0\" cellspacing=\"0\" id=\"EvaluationTable\">\n" +
                "\t\t         <tr height=\"36\" class=\"tr_bg\">\n" +
                "\t\t             <td width=\"620\" style=\"padding-left:10px; color:#666;\">å\u0090\u008Dç§°</td>\n" +
                "\t\t             <td width=\"100\" style=\"padding-left:10px; color:#666;\">ç\u008A¶æ\u0080\u0081</td>\n" +
                "\t\t             <td width=\"100\" style=\"padding-left:10px; color:#666;\">æ\u0093\u008Dä½\u009C</td>\n" +
                "\t\t         </tr>\n" +
                "\t\t         \t\t\t          <tr height=\"50\">\n" +
                "\t\t\t              <td style=\"padding-left:10px; color:#333;\">MBTIè\u0081\u008Cä¸\u009Aäººæ ¼æµ\u008Bè¯\u0084</td>\n" +
                "\t\t\t              \t\t\t\t           \t\t\t\t          \t\t<td style=\"padding-left:10px; color:#888;\">å·²å®\u008Cæ\u0088\u0090</td>\n" +
                "\t\t\t\t                <td style=\"padding-left:10px;\">\n" +
                "\t\t\t\t                \t\t\t\t                \t<a class=\"a_color mr10\" href=\"javascript:void(0);\" onclick=\"window.open('/student/tc/careerPlaning/evaluationtest.htm?id=1','blank');\">é\u0087\u008Dæµ\u008B</a>\n" +
                "\t\t\t\t                \t\t\t\t                <a class=\"a_color\" href=\"javascript:void(0);\" onclick=\"window.open('/student/tc/careerPlaning/careerEvalutionBak.htm?id=1','blank');\">æ\u009F¥ç\u009C\u008B</a>\n" +
                "\t\t\t\t                </td>\n" +
                "\t\t\t\t          \t\t\t\t\t\t          \t\t\t\t           </tr>\n" +
                "\t\t           \t\t\t          <tr height=\"50\">\n" +
                "\t\t\t              <td style=\"padding-left:10px; color:#333;\">é\u0080\u0082åº\u0094è\u0083½å\u008A\u009Bæµ\u008Béª\u008C</td>\n" +
                "\t\t\t              \t\t\t\t           \t\t\t\t          \t\t<td style=\"padding-left:10px; color:#888;\">å·²å®\u008Cæ\u0088\u0090</td>\n" +
                "\t\t\t\t                <td style=\"padding-left:10px;\">\n" +
                "\t\t\t\t                \t\t\t\t                \t<a class=\"a_color mr10\" href=\"javascript:void(0);\" onclick=\"window.open('/student/tc/careerPlaning/evaluationtest.htm?id=22','blank');\">é\u0087\u008Dæµ\u008B</a>\n" +
                "\t\t\t\t                \t\t\t\t                <a class=\"a_color\" href=\"javascript:void(0);\" onclick=\"window.open('/student/tc/careerPlaning/careerEvalutionBak.htm?id=22','blank');\">æ\u009F¥ç\u009C\u008B</a>\n" +
                "\t\t\t\t                </td>\n" +
                "\t\t\t\t          \t\t\t\t\t\t          \t\t\t\t           </tr>\n" +
                "\t\t           \t\t      </table>\n" +
                "\t\t</div>\n" +
                "   \t\t\t<input type=\"hidden\" id=\"unfinishevaluation\" value=\"0\">\n" +
                "   \t\t\t<input type=\"hidden\" id=\"nfinishevaluation\" value=\"2\">\n" +
                "\t\t   \t<script type=\"text/javascript\">\n" +
                "\t\t   \t\tif(\"0\"!=0 || \"2\"!=0)\n" +
                "\t\t\t   \t{\n" +
                "\t\t\t   \t\t$(\"#li2\").css(\"display\",\"\");\n" +
                "\t\t\t   \t\tif(\"0\" !=\"0\")\n" +
                "\t\t\t\t   \t\t{\n" +
                "\t\t\t\t   \t\t\t$(\"#li2\").text(\"æµ\u008Bè¯\u0084ï¼\u0088\"+0+\"ï¼\u0089\");\n" +
                "\t\t\t\t   \t\t}\n" +
                "\t\t\t\t   \tvar myPlanState=\"0\";\n" +
                "\t\t\t   \t\tif(myPlanState==1)\n" +
                "\t\t\t   \t\t{\n" +
                "\t\t\t\t\tpie('unfinishevaluation','nfinishevaluation','chartdiv_evaluation','evaluationfinish','evaluationunfinish');\n" +
                "\t\t\t\t\t}\n" +
                "\t\t\t   \t}\n" +
                "\t\t\t</script>\n" +
                "   \t       \t\t\t       \t\t\t\n" +
                "\t\t\t\t\t\t            <div class=\"zy_r_fl mb30\" id=\"div3\" name=\"data\" index=\"3\">\n" +
                "\t                <p class=\"zy_r_tit mb10\">è\u0080\u0083è¯\u0095</p>\n" +
                "\t                <table class=\"tab_820 jl_table\" cellpadding=\"0\" cellspacing=\"0\" id=\"ExamTable\">\n" +
                "\t                    <tr height=\"36\" class=\"tr_bg\">\n" +
                "\t                        <td width=\"320\" style=\"padding-left:10px; color:#666;\">å\u0090\u008Dç§°</td>\n" +
                "\t                        <td width=\"190\" style=\"padding-left:10px; color:#666;\">æ\u009C\u0089æ\u0095\u0088æ\u0097¶é\u0097´</td>\n" +
                "\t                        <td width=\"90\" style=\"padding-left:10px; color:#666;\">è\u0080\u0083è¯\u0095ç\u008A¶æ\u0080\u0081</td>\n" +
                "\t                        <td width=\"90\" style=\"padding-left:10px; color:#666;\">å\u008F\u0082è\u0080\u0083ç\u008A¶æ\u0080\u0081</td>\n" +
                "\t                        <td width=\"80\" style=\"padding-left:10px; color:#666;\">æ\u0088\u0090ç»©</td>\n" +
                "\t                        <td width=\"100\" style=\"padding-left:10px; color:#666;\">æ\u0093\u008Dä½\u009C</td>\n" +
                "\t                    </tr>\n" +
                "\t\t\t\t\t\t\t\t                    <tr height=\"50\">\n" +
                "\t\t                        <td style=\"padding-left:10px; color:#333;\">\n" +
                "\t\t                        \t2015çº§å\u00AD¦ç\u0094\u009Fè\u0081\u008Cä¸\u009Aå\u008F\u0091å±\u0095ä¸\u008Eå°±ä¸\u009Aæ\u008C\u0087å¯¼è¯¾å\u00AD¦ä¹ ä»»å\u008A¡ï¼\u0088äº\u0094ï¼\u0089è\u0080\u0083è¯\u0095\n" +
                "\t\t                        \t\t\t                        </td>\n" +
                "\t\t                        <td style=\"padding-left:10px; color:#888;\">\n" +
                "\t\t                        \t2018-10-08 09:56:54è\u0087³2018-12-31 23:59:59\n" +
                "\t\t                        </td>\n" +
                "\t\t                        <td style=\"padding-left:10px; color:#888;\">\n" +
                "\t\t\t\t                \t\t\t\t\t                \t\t\t\t\t                \t\tè¿\u009Bè¡\u008Cä¸\u00AD\n" +
                "\t\t\t\t                \t\t\t\t\t                \t\t\t\t\t                </td>\n" +
                "\t\t                        <td style=\"padding-left:10px; color:#888;\">\n" +
                "\t\t                        \t\t\t\t\t\t                \t\t\t\t\t\t                \t\t\t\t\t\t                \t\t\t\t\t\t                \t\t\t\t\t\t                \t\tå·²å\u008F\u0082è\u0080\u0083\n" +
                "\t\t\t\t\t                \t\t\t\t\t\t                \t\t\t\t\t\t                \t\t                        </td>\n" +
                "\t\t                        <td style=\"padding-left:10px; color:#888;\">\n" +
                "\t\t\t\t\t                \t\t\t\t\t                \t\t\t\t\t\t                \t\t\t\t\t\t\t                \t\t\tç\u00AD\u0089å¾\u0085è¯\u0084å\u0088\u0086\n" +
                "\t\t\t\t\t                \t\t\t\t\t\t\t                \t\t\t\t\t\t                \t\t\t\t                </td>\n" +
                "\t\t                        <td style=\"padding-left:10px;\">\n" +
                "\t\t                        \t\t\t\t\t                \t\t<a target=\"_blank\" href=\"../examin/result.htm?nExaminType=0&lId=29270000074\">æ\u009F¥ç\u009C\u008Bè¯\u0095å\u008D·</a>\n" +
                "\t\t\t\t                \t\t\t                        </td>\n" +
                "\t\t                    </tr>\n" +
                "\t\t                \t                </table>\n" +
                "\t            </div>\n" +
                "\t            <input type=\"hidden\" id=\"unfinishexam\" value=\"0\">\n" +
                "\t   \t\t\t<input type=\"hidden\" id=\"nfinishexam\" value=\"1\">\n" +
                "\t\t\t   \t<script type=\"text/javascript\">\n" +
                "\t\t\t   \t\tif(\"0\"!=0 || \"1\"!=0)\n" +
                "\t\t\t\t   \t{\n" +
                "\t\t\t\t   \t\t$(\"#contentshow\").css(\"display\",\"\");\n" +
                "\t\t\t\t\t   \t$(\"#wordwarn\").css(\"display\",\"none\");\n" +
                "\t\t\t\t   \t\t$(\"#li3\").css(\"display\",\"\");\n" +
                "\t\t\t\t   \t\t$(\"#totleexam\").css(\"display\",\"\");\n" +
                "\t\t\t\t   \t\tvar totledata=document.getElementById(\"totledata\").value;\n" +
                "   \t\t\t\t\t\tdocument.getElementById(\"totledata\").value=parseInt(totledata)+1;\n" +
                "   \t\t\t\t\t\t$(window).scroll();\n" +
                "\t\t\t\t   \t\tif(\"0\" !=\"0\")\n" +
                "\t\t\t   \t\t\t{\n" +
                "\t\t\t   \t\t\t\t$(\"#li3\").text(\"è\u0080\u0083è¯\u0095ï¼\u0088\"+0+\"ï¼\u0089\");\n" +
                "\t\t\t   \t\t\t}\n" +
                "\t\t\t   \t\t\tvar myPlanState=\"0\";\n" +
                "\t\t\t   \t\t\t\tif(myPlanState==1)\n" +
                "\t\t\t   \t\t\t\t{\n" +
                "\t\t\t\t\t\t\t\tpie('unfinishexam','nfinishexam','chartdiv_exam','examfinish','examunfinish');\n" +
                "\t\t\t\t\t\t\t}\n" +
                "\t\t\t\t   \t}\n" +
                "\t\t\t   \t\t\n" +
                "\t\t\t\t</script>\n" +
                "\t            \t       \t\t\t\t\t  \t       \t\t\t       \t\t\t        </ul>\n" +
                "\t        <div class=\"clear\"></div>\n" +
                "\t    </div>\n" +
                "\t</div>\n" +
                "<!--t_con END-->\n" +
                "<!--t_foot-->\n" +
                " \t\n" +
                "<div class=\"t_foot\">\n" +
                "\t\t<ul class=\"foot_w\">\n" +
                "\t\t<div class=\"foot_660\">\n" +
                "\t        <img class=\"foot_sch_logo\" src=\"http://file1.njcedu.com/img/201503/f19d16ab04b04053bb8a7c2b6da4c35c.jpg\" width=\"175\" height=\"40\"/>\n" +
                "\t        <ul class=\"foot_b\">\n" +
                "\t            <li>é\u0094¦æ\u0088\u0090ç½\u0091<img style=\"height:14px; vertical-align:middle;\" src=\"/student/image/unite.jpg\" />è\u0081\u0094å\u0090\u0088å\u009B½é\u009D\u0092å¹´å°±ä¸\u009Aç½\u0091ç»\u009Cä¸\u00ADå\u009B½ç¤ºè\u008C\u0083é¡¹ç\u009B®</li>\n" +
                "\t            <li>å®¢æ\u009C\u008Dç\u0083\u00ADçº¿ï¼\u009A400-800-8001</li>\n" +
                "\t        \t\n" +
                "\t        </ul>\n" +
                "\t        <ul class=\"foot_c\" style=\"margin-top:40px;\">\n" +
                "\t            <!-- WPA Button Begin -->\n" +
                "<!--  <script charset=\"utf-8\" type=\"text/javascript\" src=\"http://wpa.b.qq.com/cgi/wpa.php?key=XzkzODA1NzA1MF84Mjg4NF80MDAxODg5MDM2Xw\"></script>-->\n" +
                "<!-- WPA Button End -->\n" +
                "<script type='text/javascript' src='https://webchat.7moor.com/javascripts/7moorInit.js?accessId=629815e0-e617-11e7-9699-ebe89a34abd4&autoShow=true' async='async'></script>\t        </ul>\n" +
                "        </div>\n" +
                "    </ul>\n" +
                "</div>\n" +
                "<script type=\"text/javascript\" src=\"http://static.njcedu.com/js/WebView.js?v=20181107160944\" ></script>\n" +
                "\n" +
                "\n" +
                "<link type=\"text/css\" rel=\"stylesheet\" href=\"http://tool.njcedu.com/message/css/message_pattern.css?v=20181107160944\"/>\n" +
                "<script type=\"text/javascript\" src=\"http://tool.njcedu.com/message/common/js/messageinfo.js?from=student\"></script>\n" +
                "\n" +
                "<!--[if lte IE 6]>\n" +
                "\t<script>\n" +
                "\t\tisIE6=true;\n" +
                "\t</script>\n" +
                "<![endif]-->\n" +
                "\n" +
                "<script src=\"/student/common/footer.js?v=20181107160944\"></script>\n" +
                "<script type=\"text/javascript\" defer=\"defer\">\n" +
                "\t// å®\u009Aä½\u008Dmenué«\u0098äº®\n" +
                "\tlocationHighlightedMenu();\n" +
                "</script><!--t_foot END-->\n" +
                "<div class=\"zz_bg\" id=\"zzDiv\" style=\"display:none;\"></div>\n" +
                "</body>\n" +
                "</html>\n";
        //预处理数据
        String preConditionedHtml = preCondition(html);


        Document doc = Jsoup.parse(preConditionedHtml);
        Element ele = doc.select("#ExamTable").first();
        Elements tdEles = ele.select("a");
        List<String> ids = tdEles.parallelStream()
                .map(tempEle ->{
                    return tempEle.attr("href");
                })
                .collect(Collectors.toList());
        return;
    }

    private static String preCondition(String html) {
        return html.replaceAll("\n","").replaceAll("\t","").replaceAll("\"\"","");
    }
}
