package com.lottery.biying.bean;

import org.json.JSONObject;

/**
 * Created by Administrator on 2017/4/4.
 */
public class BannerBean {
   String bannerName,bannerType,cateCode,createTime,gameCode,imageHeight,imageUrl,articleId,articleType,
           imageWidth,level,linkGroupId,linkName,linkType,linkUrl,modifyTime,openLinkType,platformType,pxh,typeCode;

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getArticleType() {
        return articleType;
    }

    public void setArticleType(String articleType) {
        this.articleType = articleType;
    }

    public String getBannerName() {
        return bannerName;
    }

    public void setBannerName(String bannerName) {
        this.bannerName = bannerName;
    }

    public String getBannerType() {
        return bannerType;
    }

    public void setBannerType(String bannerType) {
        this.bannerType = bannerType;
    }

    public String getCateCode() {
        return cateCode;
    }

    public void setCateCode(String cateCode) {
        this.cateCode = cateCode;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getGameCode() {
        return gameCode;
    }

    public void setGameCode(String gameCode) {
        this.gameCode = gameCode;
    }

    public String getImageHeight() {
        return imageHeight;
    }

    public void setImageHeight(String imageHeight) {
        this.imageHeight = imageHeight;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageWidth() {
        return imageWidth;
    }

    public void setImageWidth(String imageWidth) {
        this.imageWidth = imageWidth;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getLinkGroupId() {
        return linkGroupId;
    }

    public void setLinkGroupId(String linkGroupId) {
        this.linkGroupId = linkGroupId;
    }

    public String getLinkName() {
        return linkName;
    }

    public void setLinkName(String linkName) {
        this.linkName = linkName;
    }

    public String getLinkType() {
        return linkType;
    }

    public void setLinkType(String linkType) {
        this.linkType = linkType;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getOpenLinkType() {
        return openLinkType;
    }

    public void setOpenLinkType(String openLinkType) {
        this.openLinkType = openLinkType;
    }

    public String getPlatformType() {
        return platformType;
    }

    public void setPlatformType(String platformType) {
        this.platformType = platformType;
    }

    public String getPxh() {
        return pxh;
    }

    public void setPxh(String pxh) {
        this.pxh = pxh;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public static BannerBean HandleJson(JSONObject pushjson)
    {
        BannerBean gameBean1 = new BannerBean();
        gameBean1.setArticleId(pushjson.optString("articleId", ""));
        gameBean1.setArticleType(pushjson.optString("articleType", ""));
        gameBean1.setCateCode(pushjson.optString("cateCode", ""));
        gameBean1.setCreateTime(pushjson.optString("createTime", ""));
        gameBean1.setGameCode(pushjson.optString("gameCode", ""));
        gameBean1.setImageUrl(pushjson.optString("imageUrl", ""));
        gameBean1.setLevel(pushjson.optString("level", ""));
        gameBean1.setLinkGroupId(pushjson.optString("linkGroupId", ""));
        gameBean1.setLinkName(pushjson.optString("linkName", ""));
        gameBean1.setLinkType(pushjson.optString("linkType", ""));
        gameBean1.setLinkUrl(pushjson.optString("linkUrl", ""));
        gameBean1.setBannerName(pushjson.optString("pushTitle", ""));
        gameBean1.setOpenLinkType(pushjson.optString("openLinkType", ""));
        gameBean1.setPlatformType(pushjson.optString("platformType", ""));
        gameBean1.setTypeCode(pushjson.optString("typeCode", ""));
        return gameBean1;
    }
}
