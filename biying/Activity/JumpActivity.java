package com.lottery.biying.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

//import com.a8android888.bocforandroid.BaseParent.BaseApplication;
//import com.a8android888.bocforandroid.Bean.ModuleBean;
//import com.a8android888.bocforandroid.activity.main.Sports.Sports_eventsActivity;
//import com.a8android888.bocforandroid.activity.main.lottery.LotteryFragement;
//import com.a8android888.bocforandroid.activity.user.LoginActivity;
//import com.a8android888.bocforandroid.util.AuthorizeActivity;
//import com.a8android888.bocforandroid.util.BundleTag;
//import com.a8android888.bocforandroid.util.Httputils;
//import com.a8android888.bocforandroid.util.LogTools;
//import com.a8android888.bocforandroid.util.MyJsonHttpResponseHandler;
//import com.a8android888.bocforandroid.util.ToastUtil;
//import com.a8android888.bocforandroid.webActivity;
import com.loopj.android.http.RequestParams;
import com.lottery.biying.BaseParent.BaseApplication;
import com.lottery.biying.bean.ModuleBean;
import com.lottery.biying.util.BundleTag;
import com.lottery.biying.util.LogTools;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/9/27.
 */
public class JumpActivity {
//
//    public static void modeluonclick(Context context, String linktype, String openlinktype, String typeCode,
//                                     String Level, String CateCode, String gamecode, String ArticleName, String title, String linkUrl
//            , String ArticleType, String ArticleId, String ispull, String bannerListtype, String LinkGroupId) {
//        Activity activity = null;
//        if (!(context instanceof Activity)) {
//            activity = ((BaseApplication) context.getApplicationContext()).activity;
//            if (activity == null)
//                return;
//        } else {
//            activity = (Activity) context;
//        }
//        if (linktype.equalsIgnoreCase("1")) {
//            if (openlinktype.equalsIgnoreCase("1")) {
//                Intent intent = new Intent(activity, webActivity.class);
//                intent.putExtra(BundleTag.title, title);
//                if (ispull.equalsIgnoreCase("pull")) {
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                }
//                intent.putExtra(BundleTag.URL, linkUrl);
//                intent.putExtra(BundleTag.IntentTag, true);
//                activity.startActivity(intent);
//            } else if (openlinktype.equalsIgnoreCase("2")) {
//                Intent intent = new Intent();
//                if (ispull.equalsIgnoreCase("pull")) {
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                }
//                intent.setAction("android.intent.action.VIEW");
//                Uri content_url = Uri.parse(linkUrl);
//                intent.setData(content_url);
//                activity.startActivity(intent);
//            } else {
//                Intent intent = new Intent(activity, webActivity.class);
//                intent.putExtra(BundleTag.title, title);
//                if (ispull.equalsIgnoreCase("pull")) {
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                }
//                intent.putExtra(BundleTag.URL, linkUrl);
//                intent.putExtra(BundleTag.IntentTag, true);
//                activity.startActivity(intent);
//            }
//        }
//        if (linktype.equalsIgnoreCase("2")) {
//
//            if (typeCode.equalsIgnoreCase("sport")) {
//                if (Level.equalsIgnoreCase("2")) {
//                    if (CateCode.equalsIgnoreCase("sb")) {
//
//                        GetWebdata.GetData(activity, CateCode, gamecode, ArticleName, ispull);
//                        return;
//                    } else {
//                        Jumpdata.getdatasport(activity, CateCode, ispull);
//                    }
//                } else if (Level.equalsIgnoreCase("3")) {
//
//                    GetWebdata.GetData(activity, CateCode, gamecode, ArticleName, ispull);
//                    return;
//                } else {
//                    Intent intent = new Intent(context, Sports_eventsActivity.class);
//                    if (ispull.equalsIgnoreCase("pull")) {
//                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    }
//                    context.startActivity(intent);
//                }
//            }
//            if (typeCode.equalsIgnoreCase("electronic")) {
//                if (Level.equalsIgnoreCase("2")) {
//                    if (CateCode.equalsIgnoreCase("ag")) {
//                        if (!IsLogin(activity, ispull)) return;
//                        GetWebdata.GetData(activity, CateCode, gamecode, ArticleName, ispull);
//                        return;
//                    } else {
//                        Jumpdata.getdata(activity, CateCode, ispull);
//                    }
//
//                } else if (Level.equalsIgnoreCase("3")) {
//                    if (!IsLogin(activity, ispull)) return;
//                    GetWebdata.GetData(activity, CateCode, gamecode, ArticleName, ispull);
//                    return;
//                } else {
//                    Intent intent = new Intent(context, Electronic_gamesActivity.class);
//                    if (ispull.equalsIgnoreCase("pull")) {
//                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    }
//                    context.startActivity(intent);
//                }
//
//            }
//            if (typeCode.equalsIgnoreCase("lottery")) {
//                if (Level.equalsIgnoreCase("2")) {
//                    LogTools.e("json", CateCode + "");
//                    Jumpdata.getdata2(activity, CateCode, ispull);
//                } else if (Level.equalsIgnoreCase("3")) {
//
//                    GetWebdata.GetData(activity, CateCode, gamecode, ArticleName, ispull);
//                    return;
//                } else {
//                    Intent intent = new Intent(context, LotteryFragement.class);
//                    if (ispull.equalsIgnoreCase("pull")) {
//                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    }
//                    context.startActivity(intent);
//                }
//
//            }
//            if (typeCode.equalsIgnoreCase("live")) {
//                if (Level.equalsIgnoreCase("2")) {
//                    if (!IsLogin(activity, ispull)) return;
//                    GetWebdata.GetData(activity, CateCode, gamecode, title, ispull);
//                    return;
//                } else if (Level.equalsIgnoreCase("3")) {
//                    if (!IsLogin(activity, ispull)) return;
//                    GetWebdata.GetData(activity, CateCode, gamecode, ArticleName, ispull);
//                    return;
//                } else {
//                    Intent intent = new Intent(context, Real_videoActivity.class);
//                    if (ispull.equalsIgnoreCase("pull")) {
//                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    }
//                    context.startActivity(intent);
//                }
//
//            }
//            if (typeCode.equalsIgnoreCase("card")) {
//                if (Level.equalsIgnoreCase("2")) {
//                    if (!IsLogin(activity, ispull)) return;
//                    GetWebdata.GetData(activity, CateCode, gamecode, title, ispull);
//                    return;
//                } else if (Level.equalsIgnoreCase("3")) {
//                    if (!IsLogin(activity, ispull)) return;
//                    GetWebdata.GetData(activity, CateCode, gamecode, ArticleName, ispull);
//                    return;
//                } else {
//                    Intent intent = new Intent(context, CardActivity.class);
//                    if (ispull.equalsIgnoreCase("pull")) {
//                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    }
//                    context.startActivity(intent);
//                }
//
//            }
//            if (typeCode.equalsIgnoreCase("bbin")) {
//
//                if (Level.equalsIgnoreCase("2")) {
//                    if (!IsLogin(activity, ispull)) return;
//                    GetWebdata.GetData(activity, CateCode, gamecode, title, ispull);
//                    return;
//                } else if (Level.equalsIgnoreCase("3")) {
//                    if (!IsLogin(activity, ispull)) return;
//                    GetWebdata.GetData(activity, CateCode, gamecode, ArticleName, ispull);
//                    return;
//                } else {
//                    Intent intent = new Intent(context, BBNActivity.class);
//                    if (ispull.equalsIgnoreCase("pull")) {
//                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    }
//                    context.startActivity(intent);
//                }
//            }
//        }
//        if (linktype.equalsIgnoreCase("3")) {
//            Intent intent = new Intent(activity, ActicleListActivity.class);
//            intent.putExtra(BundleTag.title, title);
//            if (ispull.equalsIgnoreCase("pull")) {
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            }
//
//            intent.putExtra(BundleTag.id, ArticleType);
//            intent.putExtra(BundleTag.articleId, LinkGroupId);
//            intent.putExtra(BundleTag.bannerList, bannerListtype);
//            activity.startActivity(intent);
//        }
//        if (linktype.equalsIgnoreCase("4")) {
//            acticle(activity, ArticleId, ispull);
//
//        }
//
//        if (linktype.equalsIgnoreCase("5")) {
//            if (openlinktype.equalsIgnoreCase("1")) {
//                Intent intent = new Intent(activity, webActivity.class);
//                intent.putExtra(BundleTag.title, title);
//                intent.putExtra(BundleTag.IntentTag, true);
//                if (ispull.equalsIgnoreCase("pull")) {
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                }
//                AuthorizeActivity.AuthorizeActivity(activity, linkUrl, intent, 0);
//            } else if (openlinktype.equalsIgnoreCase("2")) {
//                Intent intent = new Intent();
//                if (ispull.equalsIgnoreCase("pull")) {
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                }
//                intent.setAction("android.intent.action.VIEW");
//                AuthorizeActivity.AuthorizeActivity(activity, linkUrl, intent, 1);
//            } else {
//                Intent intent = new Intent(activity, webActivity.class);
//                intent.putExtra(BundleTag.title, title);
//                if (ispull.equalsIgnoreCase("pull")) {
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                }
//                intent.putExtra(BundleTag.IntentTag, true);
//                AuthorizeActivity.AuthorizeActivity(activity, linkUrl, intent, 0);
//            }
//        }
//    }
//
//    public static void acticle(Context context, String id, final String ispull) {
//        RequestParams requestParams = new RequestParams();
//        requestParams.put("articleId", id);
//        Httputils.PostWithBaseUrl(Httputils.article, requestParams, new MyJsonHttpResponseHandler((Activity) context, true) {
//            @Override
//            public void onSuccessOfMe(JSONObject jsonObject) {
//                super.onSuccessOfMe(jsonObject);
//                LogTools.ee("dddddafadfadf", jsonObject.toString());
//                try {
//                    JSONObject jsond = jsonObject.getJSONObject("datas");
//                    ModuleBean gameBean1 = new ModuleBean();
//                    gameBean1.setCreateTime(jsond.optString("createTime", ""));
//                    gameBean1.setArticleBigImages(jsond.optString("articleBigImages", ""));
//                    gameBean1.setArticleCode(jsond.optString("articleCode", ""));
//                    gameBean1.setLinkType(jsond.optString("linkType", ""));
//                    gameBean1.setLinkUrl(jsond.optString("linkUrl", ""));
//                    gameBean1.setOpenLinkType(jsond.optString("openLinkType", ""));
//                    gameBean1.setArticleSmallImages(jsond.optString("articleSmallImages", ""));
//                    gameBean1.setArticleBigHeight(jsond.optString("articleBigHeight", ""));
//                    gameBean1.setArticleSmallHeight(jsond.optString("articleSmallHeight", ""));
//                    gameBean1.setBigBackgroundPic(jsond.optString("backgroundPicUrl", ""));
//                    gameBean1.setArticleId(jsond.optString("articleId", ""));
//                    gameBean1.setArticleName(jsond.optString("articleName", ""));
//                    gameBean1.setArticleBigWidth(jsond.optString("articleBigWidth", ""));
//                    gameBean1.setArticleTitle(jsond.optString("articleTitle", ""));
//                    gameBean1.setArticleSmallWidth(jsond.optString("articleSmallWidth", ""));
//                    gameBean1.setArticleSubTitle(jsond.optString("articleSubTitle", ""));
//                    gameBean1.setArticleContent(jsond.optString("articleContent", ""));
//                    gameBean1.setShowType(jsond.optString("showType", ""));
//                    gameBean1.setArticleType(jsond.optString("articleType", ""));
//                    gameBean1.setTypeCode(jsond.optString("typeCode", ""));
//                    gameBean1.setGameCode(jsond.optString("gameCode", ""));
//                    gameBean1.setCateCode(jsond.optString("cateCode", ""));
//                    LogTools.e("dianji", jsond.optString("typeCode", ""));
//
//                    if (gameBean1.getShowType().equalsIgnoreCase("2")) {
//                        Intent intent4 = new Intent(context, webActivity.class);
//                        intent4.putExtra(BundleTag.WebData, true);
//                        if (ispull.equalsIgnoreCase("pull")) {
//                            intent4.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        }
//                        intent4.putExtra(BundleTag.URL, gameBean1.getArticleContent());
//                        intent4.putExtra(BundleTag.title, gameBean1.getArticleTitle());
//                        context.startActivity(intent4);
//                    }
//                    if (gameBean1.getShowType().equalsIgnoreCase("1")) {
//                        Intent intent4 = new Intent(context, webActivity.class);
//                        intent4.putExtra(BundleTag.WebData, true);
//                        if (ispull.equalsIgnoreCase("pull")) {
//                            intent4.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        }
//                        intent4.putExtra(BundleTag.isimg, true);
//                        intent4.putExtra(BundleTag.URL, gameBean1.getArticleBigImages());
//                        intent4.putExtra(BundleTag.title, gameBean1.getArticleTitle());
//                        context.startActivity(intent4);
//                    }
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailureOfMe(Throwable throwable, String s) {
//                super.onFailureOfMe(throwable, s);
//                throwable.printStackTrace();
//            }
//        });
//    }
//
//    public static boolean IsLogin(Context context, String ispull) {
//        String Username = ((BaseApplication) context.getApplicationContext()).getBaseapplicationUsername();
//        if (Username == null || Username.equalsIgnoreCase("")) {
//            ToastUtil.showMessage(context, "未登录");
//            Intent intent = new Intent(context, LoginActivity.class);
//            if (ispull.equalsIgnoreCase("pull")) {
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            }
//            context.startActivity(intent);
//            return false;
//        }
//        return true;
//    }
}
