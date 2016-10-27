package com.salmon.test.enums;

/**
 * Created by akepativ on 13/07/2016.
 */
public enum DatabaseQueries {

    SCHEMA("WCUSER"),
    SCHEMA_STG("WCUSER_STG"),
    SELECT_STLOCATTR("SELECT * FROM STLOCATTR WHERE LANGUAGE_ID = ? AND STLOC_ID=?"),
    SELECT_CATGROUP("select identifier from CATGROUP where identifier = '|categoryIdentifier|'"),
    SELECT_CATGRPDESC("select name from CATGRPDESC where name = '|descName|'"),
    SELECT_SYSDATE("SELECT SYSDATE FROM DUAL"),

    // STAGE TWO
    ST2_SELECT_CATGROUP("select  cg.catgroup_id, cg.member_id,cg.identifier, to_char(lastupdate,'YYYY-MM-DD HH24:MI:SS.FF') as LASTUPDATE from catgroup cg, catalog c WHERE  cg.lastupdate >  to_timestamp('|sysTime|','YYYY-MM-dd HH24:MI:SS.FF')  and c.member_id =cg.member_id and c.identifier='|catalogIdentifier|' ORDER BY LASTUPDATE asc"),
    ST2_SELECT_CATGRPDESC("select cd.catgroup_id, cd.language_id, cd.name from catgrpdesc cd where  cd.catgroup_id in (select cg.catgroup_id from catgroup cg, catalog c WHERE (cg.lastupdate >  to_timestamp('|sysTime|','YYYY-MM-dd HH24:MI:SS.FF')) and c.member_id =cg.member_id and c.identifier='|catalogIdentifier|')"),
    ST2_SELECT_CATGRPREL("select catgroup_id_parent, catgroup_id_child, catalog_id , sequence , to_char(lastupdate,'YYYY-MM-DD HH24:MI:SS.FF') as LASTUPDATE from catgrprel" +
            " where catgroup_id_parent in ((select cg.catgroup_id from catgroup cg, catalog c" +
            " WHERE (cg.lastupdate >  to_timestamp('|sysTime|','YYYY-MM-dd HH24:MI:SS.FF'))" +
            " and c.member_id =cg.member_id" +
            " and c.identifier='|catalogIdentifier|'))" +
            " and catgroup_id_child in((select cg.catgroup_id from catgroup cg, catalog c " +
            " WHERE (cg.lastupdate >  to_timestamp('|sysTime|','YYYY-MM-dd HH24:MI:SS.FF')) " +
            " and c.member_id =cg.member_id" +
            " and c.identifier='|catalogIdentifier|'))" +
            " and catalog_id = (select catalog_id from catalog where identifier ='|catalogIdentifier|') ORDER BY LASTUPDATE asc"),

    ST2_PRODUCT_CATENTRY("select partnumber, mfpartnumber, catenttype_id, mfname,field1, to_char(lastupdate,'YYYY-MM-DD HH24:MI:SS.FF') as LASTUPDATE " +
            "from catentry " +
            "where (lastupdate >  to_timestamp('|sysTime|','YYYY-MM-dd HH24:MI:SS.FF')) " +
            ""),

    ST2_PRODUCT_STORECENT("select *    from storecent " +
            "where catentry_id in (select catentry_id from catentry " +
            "where (lastupdate >  to_timestamp('|sysTime|','YYYY-MM-dd HH24:MI:SS.FF')))"),

    ST2_PRODUCT_CATENTREL("select catentry_id_child from catentrel " +
            "where catentry_id_child in (select catentry_id from catentry " +
            "where (lastupdate >  to_timestamp('|sysTime|','YYYY-MM-dd HH24:MI:SS.FF')))"),


    ST2_PRODUCT_DESCRIPTION("select  catentry_id from  catentdesc" +
            " where catentry_id in (select catentry_id from catentry " +
            "where (lastupdate >  to_timestamp('|sysTime|','YYYY-MM-dd HH24:MI:SS.FF')))"),

    ST2_PRODUCT_XDESCRIPTION("select catentry_id from xcatentdesc " +
            " where catentry_id in (select catentry_id from catentry " +
            "where (lastupdate >  to_timestamp('|sysTime|','YYYY-MM-dd HH24:MI:SS.FF')))"),

    ST2_PRODUCT_CATEGORY("select catentry_id, to_char(lastupdate,'YYYY-MM-DD HH24:MI:SS.FF') as LASTUPDATE from  catgpenrel cgr " +
            "where catentry_id in (select catentry_id from catentry " +
            "where (lastupdate >  to_timestamp('|sysTime|','YYYY-MM-dd HH24:MI:SS.FF')))"),

    SELECT_CATEGORY(
            "select c.identifier as Catalog_Identifier, cg.identifier as Category_Identifier, cog.sequence " +
                    "from catalog c, catgroup cg, cattogrp cog " +
                    "where c.catalog_id = cog.catalog_id " +
                    "and cg.catgroup_id = cog.catgroup_id " +
                    "and cg.identifier = '|categoryIdentifier|'" +
                    ""),
    SELECT_CATEGORY_DESCRIPTION(
            "select cg.identifier as Group_identifier, l.LOCALENAME as language, cd.name " +
                    "from catgroup cg, language l, catgrpdesc cd " +
                    "where cd.language_id = l.language_id " +
                    "and cg.catgroup_id = cd.catgroup_id " +
                    "and cg.identifier = '|groupIdentifier|'" +
                    ""),

    SELECT_CATEGORY_RELATIONSHIP(
            "select cg1.identifier as group_identifier, cg2.identifier as parent_group_identifier, c.identifier as catalog_identifier, cr.sequence " +
                    "from catgroup cg1, catgroup cg2,  catalog c, catgrprel cr " +
                    "where cr.catgroup_id_parent = cg2.catgroup_id" +
                    " and cr.catgroup_id_child = cg1.catgroup_id " +
                    "and cr.catalog_id = c.catalog_id " +
                    "and cg1.identifier = '|groupIdentifier|'" +
                    "and cg2.identifier = '|parentGroupIdentifier|'" +
                    ""),

    SELECT_PRODUCT_DESCRIPTION_PRODUCT("select  xcd.language_id, c.partnumber, xcd.extendedname, xcd.editorialdescription, xcd.detailsandcare, xcd.sizeandfit, cd.longdescription, cd.published " +
            " from catentry c, xcatentdesc xcd, catentdesc cd" +
            " where c.catentry_id = xcd.catentry_id" +
            " and c.catentry_id = cd.catentry_id" +
            " and xcd.language_id = cd.language_id" +
            " and c.partnumber = '|partnumber|'" +
            " and cd.language_id = '|languageId|'"),


    SELECT_PRODUCT_CATENTRY("select partnumber, mfpartnumber, catenttype_id, mfname,field1 " +
            "from catentry " +
            "where partnumber = '|partnumber|' and MFNAME='|mfname|'" +
            ""),

    SELECT_CATENTREL_PRODUCT("select * from catentrel " +
            "where catentry_id_child = (select catentry_id from catentry " +
            "where partnumber like '|catEntryChildId|' and mfname like '|mfname|') " +
            "and catreltype_id = '|catRelTypeId|' and catentry_id_parent = (select catentry_id from catentry " +
            "where partnumber like '|catEntryParentId|' and mfname like '|mfname|')" +
            ""),

    SELECT_PRODUCT_CATEGORY("select c.partnumber, cg.identifier as Category_identifier, cl.identifier as catalog_identifier, cgr.sequence " +
            "from catentry c, catgroup cg, catalog cl, catgpenrel cgr " +
            "where c.catentry_id = cgr.catentry_id " +
            "and cg.catgroup_id = cgr.catgroup_id " +
            "and cl.catalog_id = cgr.catalog_id " +
            "and c.partnumber = '|partnumber|' " +
            "and cg.identifier = '|categoryIdentifier|'" +
            ""),

    SELECT_ATTRIBUTE("select cat.identifier as parentstoreidentifier, attr.identifier as attributeidentifier, attrdesc.language_id, attrdesc.name, attr.attrusage " +
            "from attr attr, attrdesc attrdesc, catalog cat, storecat scat " +
            "where cat.catalog_id = scat.catalog_id and attr.attr_id = attrdesc.attr_id and attr.storeent_id = scat.storeent_id and " +
            "cat.identifier = '|categoryIdentifier|' and attr.identifier ='|attributeIdentifier|' and attrdesc.language_id = '|languageId|' " +
            ""),

    SELECT_ATTRIBUTE_VALUES("select cat.identifier as parentstoreidentifier, attrval.identifier as attributevalueidentifier, attr.identifier as attributeidentifier, " +
            "attrval.field1, attrvaldesc.language_id, attrvaldesc.value, attrvaldesc.sequence, attrval.valusage " +
            "from attr attr, attrval attrval, attrvaldesc attrvaldesc, catalog cat, storecat scat " +
            "where cat.catalog_id = scat.catalog_id and attr.attr_id = attrval.attr_id and attrval.attrval_id = attrvaldesc.attrval_id and " +
            "attrval.storeent_id = scat.storeent_id and cat.identifier = '|categoryIdentifier|' and attrval.identifier = '|attributeValueIdentifier|' and attrvaldesc.language_id = '|languageId|' " +
            ""),

    SELECT_PRODUCT_ATTRIBUTE_VALUES("select c.partnumber, cat.identifier as parentstoreidentifier, " +
            "attrval.identifier as attributevalueidentifier, attr.identifier as attributeidentifier, cattr.usage, cattr.sequence " +
            "from catentry c, catentryattr cattr, catalog cat, storecat scat, attr attr, attrval attrval " +
            "where cat.catalog_id = scat.catalog_id and c.catentry_id = cattr.catentry_id and attr.attr_id = cattr.attr_id and attr.storeent_id = scat.storeent_id and cattr.attrval_id = attrval.attrval_id  " +
            "and cat.identifier = '|categoryIdentifier|'and c.partnumber = '|partNumber|' and attrval.identifier = '|attributeValueIdentifier|' " +
            ""),

    SELECT_PRODUCT_PRICE_OFFER("select * from offer where catentry_id in (select catentry_id from catentry where partnumber in( '|partNumber|'))"),
    SELECT_PRODUCT_PRICE_OFFERPRICE("select * from offerprice where offer_id in (select offer_id from offer where catentry_id in (select catentry_id from catentry where partnumber in( '|partNumber|')))"),
    SELECT_PRODUCT_PRICE_XOFFERPRICE("select * from xofferprice where offer_id in (select offer_id from offer where catentry_id in (select catentry_id from catentry where partnumber in( '|partNumber|')))"),

    SELECT_CATENTRYATTR("select c.partnumber, a.IDENTIFIER as attribute_identifier, av.IDENTIFIER as attribute_value_identifier" +
            "from catentry c, attr a, attrval av, catentryattr ca" +
            "where c.catentry_id = ca.catentry_id" +
            "and a.attr_id = ca.attr_id" +
            "and av.attrval_id = ca.attrval_id" +
            "and c.partnumber = |gtin|" +
            ""),

    SELECT_LANG_FOR_STORE("Select str.LANGUAGE_ID languageId,lang.COUNTRY country,lang.LOCALENAME localeName,lang.LANGUAGE language " +
            " From STORE str, language lang" +
            " WHERE str.STORE_ID = '|STORE_ID|' AND str.LANGUAGE_ID = lang.LANGUAGE_ID"),
    GET_LANGUAGE_ID_BY_LOCALE_NAME("Select lang.LANGUAGE_ID languageId From language lang" +
            " Where lang.LOCALENAME = '|LOCALE_NAME|'"),
    GET_REGIONS_FOR_STORE("Select xrgn.REGION_CODE regionCode,xrgndsc.NAME regionName,ctr.COUNTRYABBR countryCode,ctr.NAME countryName,stloc.CITY city " +
            " From STLOC stloc,XSTLOC xstloc,COUNTRY ctr,XREGION xrgn,XREGIONCOUNTRY xrgnctr,XREGIONDESC xrgndsc " +
            " WHERE stloc.STOREENT_ID = '|STORE_ID|' AND stloc.STLOC_ID = xstloc.STLOC_ID " +
            " AND xstloc.COUNTRYABBR = ctr.COUNTRYABBR AND ctr.LANGUAGE_ID = |LANGUAGE_ID| " +
            " AND xrgnctr.COUNTRYABBR = xstloc.COUNTRYABBR AND xrgnctr.REGION_ID = xrgn.REGION_ID AND xrgndsc.REGION_ID = xrgn.REGION_ID " +
            " AND xrgndsc.LANGUAGE_ID = |LANGUAGE_ID| " +
            " order by regionName,countryName,city"),
    GET_REGIONS_COUNT_FOR_STORE("Select COUNT(DISTINCT xrgn.REGION_CODE) regionCount " +
            " From STLOC stloc,XSTLOC xstloc,COUNTRY ctr,XREGION xrgn,XREGIONCOUNTRY xrgnctr,XREGIONDESC xrgndsc  WHERE stloc.STOREENT_ID = '|STORE_ID|' " +
            " AND stloc.STLOC_ID = xstloc.STLOC_ID  AND xstloc.COUNTRYABBR = ctr.COUNTRYABBR AND ctr.LANGUAGE_ID = |LANGUAGE_ID|  " +
            " AND xrgnctr.COUNTRYABBR = xstloc.COUNTRYABBR" +
            " AND xrgnctr.REGION_ID = xrgn.REGION_ID AND xrgndsc.REGION_ID = xrgn.REGION_ID  AND xrgndsc.LANGUAGE_ID = |LANGUAGE_ID| "),
    GET_COUNTRIES_BY_REGION_COUNT_FOR_STORE("Select COUNT(DISTINCT ctr.COUNTRYABBR) countryCount " +
            " From STLOC stloc,XSTLOC xstloc,COUNTRY ctr,XREGION xrgn,XREGIONCOUNTRY xrgnctr,XREGIONDESC xrgndsc  WHERE stloc.STOREENT_ID = '|STORE_ID|' " +
            " AND stloc.STLOC_ID = xstloc.STLOC_ID  AND xstloc.COUNTRYABBR = ctr.COUNTRYABBR AND ctr.LANGUAGE_ID = |LANGUAGE_ID|  " +
            " AND xrgn.REGION_CODE = '|REGION_CODE|' AND xrgnctr.COUNTRYABBR = xstloc.COUNTRYABBR" +
            " AND xrgnctr.REGION_ID = xrgn.REGION_ID AND xrgndsc.REGION_ID = xrgn.REGION_ID  AND xrgndsc.LANGUAGE_ID = |LANGUAGE_ID| "),
    GET_COUNTRIES_FOR_STORE("Select distinct ctr.COUNTRYABBR countryCode,ctr.NAME countryName,stloc.CITY city " +
            " From STLOC stloc,XSTLOC xstloc,COUNTRY ctr " +
            " WHERE stloc.STOREENT_ID = '|STORE_ID|'  AND stloc.STLOC_ID = xstloc.STLOC_ID " +
            " AND xstloc.COUNTRYABBR = ctr.COUNTRYABBR AND ctr.LANGUAGE_ID = |LANGUAGE_ID| " +
            " order by countryName,city"),
    GET_TOTAL_COUNTRIES_FOR_STORE("Select COUNT(DISTINCT ctr.COUNTRYABBR) countryCount " +
            " From STLOC stloc,XSTLOC xstloc,COUNTRY ctr " +
            " WHERE stloc.STOREENT_ID = '|STORE_ID|'  AND stloc.STLOC_ID = xstloc.STLOC_ID " +
            " AND xstloc.COUNTRYABBR = ctr.COUNTRYABBR AND ctr.LANGUAGE_ID = |LANGUAGE_ID| "),
    GET_CITIES_BY_COUNTRY_FOR_STORE("Select distinct ctr.COUNTRYABBR countryCode,ctr.NAME countryName,stloc.CITY city  From STLOC stloc,XSTLOC xstloc,COUNTRY ctr  " +
            " WHERE stloc.STOREENT_ID = '|STORE_ID|' and xstloc.COUNTRYABBR = '|COUNTRY_CODE|' AND stloc.STLOC_ID = xstloc.STLOC_ID  AND xstloc.COUNTRYABBR = ctr.COUNTRYABBR " +
            " AND ctr.LANGUAGE_ID = |LANGUAGE_ID|   order by countryName,city"),
    GET_COUNTRIES_BY_REGION_FOR_STORE("Select distinct xrgn.REGION_CODE regionCode,xrgndsc.NAME regionName,ctr.COUNTRYABBR countryCode,ctr.NAME countryName,stloc.CITY city " +
            " From STLOC stloc,XSTLOC xstloc,COUNTRY ctr,XREGION xrgn,XREGIONCOUNTRY xrgnctr,XREGIONDESC xrgndsc " +
            "  WHERE stloc.STOREENT_ID = '|STORE_ID|' AND stloc.STLOC_ID = xstloc.STLOC_ID  AND xrgn.REGION_CODE = '|REGION_CODE|' " +
            "  AND xstloc.COUNTRYABBR = ctr.COUNTRYABBR AND ctr.LANGUAGE_ID = |LANGUAGE_ID|  AND xrgnctr.COUNTRYABBR = xstloc.COUNTRYABBR AND xrgnctr.REGION_ID = xrgn.REGION_ID " +
            "   AND xrgndsc.REGION_ID = xrgn.REGION_ID  AND xrgndsc.LANGUAGE_ID = |LANGUAGE_ID|  order by regionName,countryName,city"),
    GET_STLOC_ID_FOR_ATTRIBUTE(" Select distinct stloc.STLOC_ID from XSTLOC xstloc, stloc stloc,XSTLOCATTR xstlocattr " +
            "   WHERE stloc.STOREENT_ID = '|STORE_ID|' AND stloc.STLOC_ID = xstloc.STLOC_ID AND  xstlocattr.STLOC_ID = stloc.STLOC_ID " +
            "   AND xstlocattr.ATTR_ID = (SELECT attr.ATTR_ID FROM ATTR attr WHERE attr.IDENTIFIER = '|ATTR_IDENTIFIER|' AND attr.STOREENT_ID = '|STORE_ID|' AND attr.FIELD1 = 3 ) " +
            "   AND xstlocattr.ATTRVAL_ID = (SELECT attrval.ATTRVAL_ID FROM ATTRVAL attrval WHERE attrval.IDENTIFIER = '|ATTRVAL_IDENTIFIER|' AND attrval.STOREENT_ID = '|STORE_ID|')"),
    GET_COUNTRIES_WITH_ATTRIBUTES_FOR_STORE("Select distinct ctr.COUNTRYABBR countryCode,ctr.NAME countryName,stloc.CITY city From STLOC stloc,XSTLOC xstloc,COUNTRY ctr ,XSTLOCATTR xstlocattr " +
            "   WHERE stloc.STOREENT_ID = '|STORE_ID|'  AND stloc.STLOC_ID = xstloc.STLOC_ID  AND xstloc.COUNTRYABBR = ctr.COUNTRYABBR AND ctr.LANGUAGE_ID = |LANGUAGE_ID|   " +
            "   AND stloc.STLOC_ID IN ( "),
    GET_CITIES_BY_COUNTRY_WITH_ATTRIBUTES_FOR_STORE("Select distinct ctr.COUNTRYABBR countryCode,ctr.NAME countryName,stloc.CITY city From STLOC stloc,XSTLOC xstloc,COUNTRY ctr ,XSTLOCATTR xstlocattr " +
            "   WHERE stloc.STOREENT_ID = '|STORE_ID|'  AND xstloc.COUNTRYABBR = '|COUNTRY_CODE|' AND stloc.STLOC_ID = xstloc.STLOC_ID  AND xstloc.COUNTRYABBR = ctr.COUNTRYABBR AND ctr.LANGUAGE_ID = |LANGUAGE_ID|   " +
            "   AND stloc.STLOC_ID IN ( "),
    GET_ATTRIBUTES_FOR_STORE(" Select attr.ATTR_ID,attr.IDENTIFIER attrIdentifier,attrval.ATTRVAL_ID,attrval.IDENTIFIER attrvalueidentifier FROM ATTR attr,ATTRVAL attrval " +
            " WHERE attr.STOREENT_ID IN ('|STORE_IDS|') AND attr.ATTR_ID = attrval.ATTR_ID"),
    GET_COUNTRIES_COUNT_WITH_ATTRIBUTES_FOR_STORE("Select COUNT(DISTINCT ctr.COUNTRYABBR) countryCount From STLOC stloc,XSTLOC xstloc,COUNTRY ctr ,XSTLOCATTR xstlocattr " +
            "        WHERE stloc.STOREENT_ID = '|STORE_ID|'  AND stloc.STLOC_ID = xstloc.STLOC_ID  AND xstloc.COUNTRYABBR = ctr.COUNTRYABBR AND ctr.LANGUAGE_ID = |LANGUAGE_ID|  " +
            "        AND stloc.STLOC_ID IN ( "),
    GET_REGIONS_WITH_ATTRIBUTES_FOR_STORE("Select distinct xrgn.REGION_CODE regionCode,xrgndsc.NAME regionName,ctr.COUNTRYABBR countryCode,ctr.NAME countryName,stloc.CITY city " +
            " From STLOC stloc,XSTLOC xstloc,COUNTRY ctr,XREGION xrgn,XREGIONCOUNTRY xrgnctr,XREGIONDESC xrgndsc,XSTLOCATTR xstlocattr " +
            "  WHERE stloc.STOREENT_ID = '|STORE_ID|' AND stloc.STLOC_ID = xstloc.STLOC_ID  AND xstlocattr.STLOC_ID = stloc.STLOC_ID " +
            "  AND xstloc.COUNTRYABBR = ctr.COUNTRYABBR AND ctr.LANGUAGE_ID = |LANGUAGE_ID|  AND xrgnctr.COUNTRYABBR = xstloc.COUNTRYABBR AND xrgnctr.REGION_ID = xrgn.REGION_ID " +
            "   AND xrgndsc.REGION_ID = xrgn.REGION_ID  AND xrgndsc.LANGUAGE_ID = |LANGUAGE_ID| " +
            "    AND stloc.STLOC_ID IN ( "),
    GET_REGIONS_COUNT_WITH_ATTRIBUTES_FOR_STORE("SELECT COUNT(DISTINCT xrgn.REGION_CODE) regionCount " +
            " From STLOC stloc,XSTLOC xstloc,COUNTRY ctr,XREGION xrgn,XREGIONCOUNTRY xrgnctr,XREGIONDESC xrgndsc,XSTLOCATTR xstlocattr " +
            "  WHERE stloc.STOREENT_ID = '|STORE_ID|' AND stloc.STLOC_ID = xstloc.STLOC_ID  AND xstlocattr.STLOC_ID = stloc.STLOC_ID " +
            "  AND xstloc.COUNTRYABBR = ctr.COUNTRYABBR AND ctr.LANGUAGE_ID = |LANGUAGE_ID|  AND xrgnctr.COUNTRYABBR = xstloc.COUNTRYABBR AND xrgnctr.REGION_ID = xrgn.REGION_ID " +
            "   AND xrgndsc.REGION_ID = xrgn.REGION_ID  AND xrgndsc.LANGUAGE_ID = |LANGUAGE_ID| " +
            "    AND stloc.STLOC_ID IN ( "),
    GET_REGIONS_COUNT_BY_REGION_WITH_ATTRIBUTES_FOR_STORE("SELECT COUNT(DISTINCT ctr.COUNTRYABBR) countryCount " +
            " From STLOC stloc,XSTLOC xstloc,COUNTRY ctr,XREGION xrgn,XREGIONCOUNTRY xrgnctr,XREGIONDESC xrgndsc,XSTLOCATTR xstlocattr " +
            "  WHERE stloc.STOREENT_ID = '|STORE_ID|' AND stloc.STLOC_ID = xstloc.STLOC_ID  AND xstlocattr.STLOC_ID = stloc.STLOC_ID " +
            "  AND xstloc.COUNTRYABBR = ctr.COUNTRYABBR AND ctr.LANGUAGE_ID = |LANGUAGE_ID|  AND xrgnctr.COUNTRYABBR = xstloc.COUNTRYABBR AND xrgnctr.REGION_ID = xrgn.REGION_ID " +
            "   AND xrgn.REGION_CODE = '|REGION_CODE|' AND xrgndsc.REGION_ID = xrgn.REGION_ID  AND xrgndsc.LANGUAGE_ID = |LANGUAGE_ID| " +
            "    AND stloc.STLOC_ID IN ( "),
    GET_COUNTRIES_BY_REGION_WITH_ATTRIBUTES_FOR_STORE("Select distinct xrgn.REGION_CODE regionCode,xrgndsc.NAME regionName,ctr.COUNTRYABBR countryCode,ctr.NAME countryName,stloc.CITY city " +
            " From STLOC stloc,XSTLOC xstloc,COUNTRY ctr,XREGION xrgn,XREGIONCOUNTRY xrgnctr,XREGIONDESC xrgndsc,XSTLOCATTR xstlocattr " +
            "  WHERE stloc.STOREENT_ID = '|STORE_ID|' AND stloc.STLOC_ID = xstloc.STLOC_ID  AND xstlocattr.STLOC_ID = stloc.STLOC_ID " +
            "  AND xstloc.COUNTRYABBR = ctr.COUNTRYABBR AND ctr.LANGUAGE_ID = |LANGUAGE_ID|  AND xrgnctr.COUNTRYABBR = xstloc.COUNTRYABBR AND xrgnctr.REGION_ID = xrgn.REGION_ID " +
            "   AND xrgn.REGION_CODE = '|REGION_CODE|' AND xrgndsc.REGION_ID = xrgn.REGION_ID  AND xrgndsc.LANGUAGE_ID = |LANGUAGE_ID| " +
            "    AND stloc.STLOC_ID IN ( "),
    GET_STORE_CATALOG_ASSET_ID("Select RELATEDSTORE_ID FROM STOREREL str WHERE str.STORE_ID= '|STORE_ID|' AND " +
            "str.STRELTYP_ID = '-4' AND str.RELATEDSTORE_ID != '|STORE_ID|'"),
    GET_STORE_CATALOG_ASSET_ID_MAP(" Select st.DIRECTORY STORE_ID, RELATEDSTORE_ID STORECAS_ID FROM STOREREL str,STORE st WHERE " +
            "            str.STRELTYP_ID = '-4' AND str.STORE_ID != str.RELATEDSTORE_ID AND st.STORE_ID = str.STORE_ID "),
    GET_STORE_ID_MAP(" Select IDENTIFIER STORE_IDENTIFIER,STOREENT_ID STORE_ID  FROM STOREENT "),
    GET_PHYSICAL_STORES_FOR_SITE("Select * From (Select sl.stloc_id,xsl.STORENAME,xsl.STORENUMBER,sl.ACTIVE,sl.IDENTIFIER " +
            " From stloc sl,xstloc xsl " +
            " Where sl.STOREENT_ID = '|STORECAS_ID|' and sl.active in (|IS_ACTIVE|) AND sl.STLOC_ID = xsl.STLOC_ID " +
            " order by STORENAME) " +
            " OFFSET |OFFSET| rows fetch next |NO_OF_ROWS| rows only"),
    GET_PHYSICAL_STORE_INFO("Select sl.stloc_id,sl.address1 line1,sl.address2 line2,sl.address3 line3,sl.city,sl.STATE,sl.zipcode postCode,sl.LATITUDE,sl.LONGITUDE, " +
            " xsl.Countryabbr countryCode,ctr.NAME COUNTRY_NAME,DECODE(xrgd.NAME,'','',xrgn.REGION_CODE) REGION_CODE,xrgd.NAME REGION_NAME" +
            " From stloc sl,xstloc xsl,country ctr,XREGIONCOUNTRY xrgctr,xregion xrgn left outer join XREGIONDESC xrgd on (xrgn.REGION_ID = xrgd.REGION_ID AND xrgd.LANGUAGE_ID = |LANGUAGE_ID|) " +
            " Where sl.STOREENT_ID = '|STORECAS_ID|' and sl.active in (|IS_ACTIVE|) AND sl.STLOC_ID = xsl.STLOC_ID AND ctr.COUNTRYABBR = xsl.COUNTRYABBR AND ctr.LANGUAGE_ID = |LANGUAGE_ID| " +
            " AND xrgctr.COUNTRYABBR = xsl.COUNTRYABBR AND xrgctr.REGION_ID = xrgn.REGION_ID" +
            " order by STORENAME "),
    GET_PHYSICAL_STORE_CONTACT_INFO("SELECT sl.STLOC_ID,xc.IDENTIFIER identifier,xct.CONTACT_CODE TYPE_CODE,xctd.NAME TYPE_NAME,xc.CONTACT_NAME_ID contactNameId ,xcnd.NAME CONTACT_NAME,xc.CONTACT_VALUE CONATCT_VALUE " +
            " FROM stloc sl,xstloc_contact xslc,XCONTACT xc,XCONTACT_NAME xcn,XCONTACT_NAMEDESC xcnd,XCONTACT_TYPE xct,XCONTACT_TYPEDESC xctd " +
            " WHERE sl.STOREENT_ID = '|STORECAS_ID|' AND  sl.STLOC_ID = xslc.STLOC_ID AND sl.ACTIVE in (|IS_ACTIVE|) AND xslc.CONTACT_ID = xc.CONTACT_ID AND xcn.CONTACT_NAME_ID = xc.CONTACT_NAME_ID " +
            " AND xcn.CONTACT_NAME_ID = xcnd.CONTACT_NAME_ID AND xcnd.LANGUAGE_ID = |LANGUAGE_ID| " +
            " AND xct.CONTACT_TYPE_ID =  xc.CONTACT_TYPE_ID AND xct.CONTACT_TYPE_ID = xctd.CONTACT_TYPE_ID AND xctd.LANGUAGE_ID = |LANGUAGE_ID|  "),
    GET_PHYSICAL_STORE_ATTRIBUTES("  Select distinct  sl.STLOC_ID , xsla.ATTR_ID,atr.IDENTIFIER,atr.FIELD3 attribute_type,atrd.NAME attribute_name  " +
            "  From STLOC sl,XSTLOCATTR xsla,ATTR atr,ATTRDESC atrd,ATTRVAL atrv,ATTRVALDESC atrvd " +
            "   WHERE sl.STOREENT_ID = '|STORECAS_ID|' AND sl.ACTIVE in (|IS_ACTIVE|) AND sl.STLOC_ID = xsla.STLOC_ID AND atr.STOREENT_ID = '|STORECAS_ID|' " +
            "   AND xsla.ATTR_ID = atr.ATTR_ID  AND atr.FIELD1 = 3  AND atr.ATTR_ID =  atrd.ATTR_ID AND atrd.LANGUAGE_ID = |LANGUAGE_ID| " +
            "   AND atrv.STOREENT_ID = '|STORECAS_ID|'  AND xsla.ATTRVAL_ID = atrv.ATTRVAL_ID AND atrv.ATTRVAL_ID = atrvd.ATTRVAL_ID AND atrvd.LANGUAGE_ID = |LANGUAGE_ID| "),
    GET_PHYSICAL_STORE_ATTRIBUTE_VALUES("  Select distinct sl.STLOC_ID , xsla.ATTR_ID,xsla.ATTRVAL_ID,atrv.IDENTIFIER attribute_Value_Identifier,atrvd.VALUE attribute_Value_name " +
            "   From STLOC sl,XSTLOCATTR xsla,ATTR atr,ATTRDESC atrd,ATTRVAL atrv,ATTRVALDESC atrvd    WHERE sl.STOREENT_ID = '|STORECAS_ID|' AND sl.ACTIVE in (|IS_ACTIVE|) AND sl.STLOC_ID = xsla.STLOC_ID " +
            "   AND atr.STOREENT_ID = '|STORECAS_ID|'  AND xsla.ATTR_ID = atr.ATTR_ID  AND atr.FIELD1 = 3 AND atr.ATTR_ID =  atrd.ATTR_ID AND atrd.LANGUAGE_ID = |LANGUAGE_ID|    AND atrv.STOREENT_ID = '|STORECAS_ID|'  " +
            "   AND xsla.ATTRVAL_ID = atrv.ATTRVAL_ID AND atrv.ATTRVAL_ID = atrvd.ATTRVAL_ID AND atrvd.LANGUAGE_ID = |LANGUAGE_ID| "),
    GET_ATTRIBUTES_FOR_STORE_WITH_LANGUAGE("select attr.identifier,attr.field3, attrdesc.name,attrval.identifier videntifier,attrvaldesc.value" +
            " from attr attr,attrdesc attrdesc,attrval attrval,attrvaldesc attrvaldesc where " +
            " attrdesc.attr_id=attr.attr_id and " +
            " attrval.attr_id=attr.attr_id and " +
            " attrvaldesc.attrval_id=attrval.attrval_id and " +
            " attrdesc.language_id=attrvaldesc.language_id and " +
            " attr.field1=|STLOCATTR_FIELD1| and " +
            " attr.storeent_id='|STORECAS_ID|' and attrdesc.language_id=|LANGUAGE_ID|"),
    GET_ATTRIBUTES_COUNT_FOR_STORE_WITH_LANGUAGE("Select count(distinct attr.IDENTIFIER) attributescount FROM ATTR attr,ATTRVAL attrval, ATTRDESC attrdesc, ATTRVALDESC attrvaldesc " +
            "WHERE attr.STOREENT_ID ='|STORECAS_ID|' AND attr.FIELD1 = |STLOCATTR_FIELD1|  AND attr.ATTR_ID = attrdesc.ATTR_ID AND attrdesc.LANGUAGE_ID = |LANGUAGE_ID| " +
            "AND attrval.STOREENT_ID = '|STORECAS_ID|' AND attrval.ATTRVAL_ID = attrvaldesc.ATTRVAL_ID AND attrvaldesc.LANGUAGE_ID = |LANGUAGE_ID|"),
    CHECK_LANGUAGE_SUPPORTED_FOR_STORE("Select count(LANGUAGE_ID) from STORELANG WHERE STOREENT_ID = '|STORE_ID|' AND LANGUAGE_ID = |LANGUAGE_ID|"),
    GET_PHYSICAL_STORES_WITH_REGION_FOR_SITE(" Select * From (Select sl.stloc_id,xsl.STORENAME,xsl.STORENUMBER,sl.ACTIVE,sl.IDENTIFIER  " +
            " From stloc sl,xstloc xsl, xregion xr,xregionCountry xrc  Where sl.STOREENT_ID = '|STORECAS_ID|' and sl.active in (|IS_ACTIVE|) AND sl.STLOC_ID = xsl.STLOC_ID " +
            " AND xsl.COUNTRYABBR = xrc.COUNTRYABBR AND xr.REGION_CODE = '|REGION_CODE|' AND xrc.REGION_ID = xr.REGION_ID  order by STORENAME)  OFFSET |OFFSET| rows fetch next |NO_OF_ROWS| rows only "),
    GET_PHYSICAL_STORE_OPENING_HOURS("SELECT distinct sl.STLOC_ID,xslh.DAY DAY_NUMBER " +
            "  From STLOC sl,XSTLOC xsl,XSTLOCHOURS xslh " +
            " WHERE sl.STOREENT_ID = '|STORECAS_ID|' AND sl.STLOC_ID = xsl.STLOC_ID AND sl.STLOC_ID = xslh.STLOC_ID  AND sl.ACTIVE in (|IS_ACTIVE|)  order by STLOC_ID,DAY_NUMBER "),
    GET_PHYSICAL_STORES_WITH_COUNTRY_FOR_SITE(" Select * From (Select sl.stloc_id,xsl.STORENAME,xsl.STORENUMBER,sl.ACTIVE,sl.IDENTIFIER  " +
            "   From stloc sl,xstloc xsl  Where sl.STOREENT_ID = '|STORECAS_ID|' and sl.active in (|IS_ACTIVE|) AND sl.STLOC_ID = xsl.STLOC_ID " +
            "   AND xsl.COUNTRYABBR = '|COUNTRY_CODE|' order by STORENAME)   OFFSET |OFFSET| rows fetch next |NO_OF_ROWS| rows only"),
    GET_PHYSICAL_STORE_TIME_SLOTS("Select sl.STLOC_ID,xslh.OPENSLOT SLOT,xslh.OPENTIMEHOUR,xslh.OPENTIMEMINUTE, " +
            "  xslh.CLOSETIMEHOUR,xslh.CLOSETIMEMINUTE  From STLOC sl,XSTLOC xsl,XSTLOCHOURS xslh WHERE sl.STLOC_ID = '|STLOC_ID|' " +
            " AND sl.STLOC_ID = xsl.STLOC_ID AND sl.STLOC_ID = xslh.STLOC_ID  AND xslh.DAY = |DAY_NUMBER| order by slot "),
    GET_PHYSICAL_STORES_WITH_CITY_FOR_SITE(" Select * From (Select distinct sl.stloc_id,xsl.STORENAME,xsl.STORENUMBER,sl.ACTIVE,sl.IDENTIFIER  " +
            "   From stloc sl,xstloc xsl,xregion xr,xregionCountry xrc  Where sl.STOREENT_ID = '|STORECAS_ID|' and sl.active in (|IS_ACTIVE|) AND sl.STLOC_ID = xsl.STLOC_ID  " +
            "   AND xsl.COUNTRYABBR = '|COUNTRY_CODE|' and sl.CITY = '|CITY|' AND xr.REGION_CODE = '|REGION_CODE|' AND xsl.COUNTRYABBR = xrc.COUNTRYABBR " +
            "  AND xrc.REGION_ID = xr.REGION_ID  order by STORENAME)   OFFSET |OFFSET| rows fetch next |NO_OF_ROWS| rows only"),
    GET_PHYSICAL_STORES_WITH_REGION_AND_ATTRIBUTES("Select * From (Select sl.stloc_id,xsl.STORENAME,xsl.STORENUMBER,sl.ACTIVE,sl.IDENTIFIER  " +
            "    From stloc sl,xstloc xsl, xregion xr,xregionCountry xrc  Where sl.STOREENT_ID = '|STORECAS_ID|' and sl.active in (|IS_ACTIVE|) AND sl.STLOC_ID = xsl.STLOC_ID " +
            "    AND xsl.COUNTRYABBR = xrc.COUNTRYABBR AND xr.REGION_CODE = '|REGION_CODE|' AND xrc.REGION_ID = xr.REGION_ID  " +
            "    AND sl.STLOC_ID IN ( "),
    GET_LATITUDE_AND_LONGITUDE_FOR_STORE("Select * From (Select stloc.longitude, stloc.latitude from stloc where storeent_id = '|STORECAS_ID|') Where ROWNUM <= 1 "),
    GET_PHYSICAL_STORE_FOR_GIVEN_LATITUDE_AND_LONGITUDE("Select * From (Select sl.STLOC_ID,sl.IDENTIFIER,xsl.STORENAME,xsl.STORENUMBER,sl.ACTIVE," +
            "ROUND(SQRT( POWER(69.1 * (latitude - |LATITUDE|), 2) + POWER(69.1 * (|LONGITUDE| - longitude) * " +
            "COS(latitude / 57.3), 2))*|RADIUS_UOM|,14) AS distance,sl.LONGITUDE,sl.LATITUDE From stloc sl,xstloc xsl " +
            "Where sl.STOREENT_ID = '|STORECAS_ID|' AND sl.STLOC_ID = xsl.STLOC_ID  " +
            "AND sl.active IN (|IS_ACTIVE|) AND ROUND(SQRT( POWER(69.1 * (latitude - |LATITUDE|), 2) + POWER(69.1 * (|LONGITUDE| - longitude) * " +
            "COS(latitude / 57.3), 2))*|RADIUS_UOM|,14) < |RADIUS| ORDER BY distance,STORENAME ASC)OFFSET |OFFSET| rows fetch next |LIMIT| rows only"),
    GET_PHYSICAL_STORE_COUNT_FOR_GIVEN_LATITUDE_AND_LONGITUDE("Select Count(IDENTIFIER) As STORECOUNT From(Select * From (Select sl.STLOC_ID,sl.IDENTIFIER,xsl.STORENAME,xsl.STORENUMBER,sl.ACTIVE," +
            "ROUND(SQRT( POWER(69.1 * (latitude - |LATITUDE|), 2) + POWER(69.1 * (|LONGITUDE| - longitude) * " +
            "COS(latitude / 57.3), 2))*|RADIUS_UOM|,14) AS distance,sl.LONGITUDE,sl.LATITUDE From stloc sl,xstloc xsl " +
            "Where sl.STOREENT_ID = '|STORECAS_ID|' AND sl.STLOC_ID = xsl.STLOC_ID " +
            "AND sl.active IN (|IS_ACTIVE|) AND SQRT(POWER(69.1 * (latitude - |LATITUDE|), 2) +" +
            " POWER(69.1 * (|LONGITUDE| - longitude) * COS(latitude / 57.3), 2)) < |RADIUS| ORDER BY distance ASC)OFFSET |OFFSET| rows fetch next |LIMIT| rows only)"),
    GET_PHYSICAL_STORES_WITH_ATTRIBUTES(" Select * From (Select sl.stloc_id,xsl.STORENAME,xsl.STORENUMBER,sl.ACTIVE,sl.IDENTIFIER  " +
            "  From stloc sl,xstloc xsl  Where sl.STOREENT_ID = '|STORECAS_ID|' and sl.active in (|IS_ACTIVE|) AND sl.STLOC_ID = xsl.STLOC_ID  " +
            "  AND sl.STLOC_ID IN ( "),
    GET_PHYSICAL_STORES_WITH_COUNTRY_AND_ATTRIBUTES("SELECT * From (Select sl.stloc_id,xsl.STORENAME,xsl.STORENUMBER,sl.ACTIVE,sl.IDENTIFIER  " +
            "   From stloc sl,xstloc xsl  Where sl.STOREENT_ID = '|STORECAS_ID|' and sl.active in (|IS_ACTIVE|) AND sl.STLOC_ID = xsl.STLOC_ID " +
            "   AND xsl.COUNTRYABBR = '|COUNTRY_CODE|' and sl.CITY = '|CITY|' AND sl.STLOC_ID IN (  "),
    GET_STORE_ID("select STOREENT_ID from STOREENT where IDENTIFIER= '|identifier|'"),
    GET_PHYSICAL_STORE_FOR_GIVEN_LATITUDE_AND_LONGITUDE_WITH_ATTRIBUTES("Select * From (Select sl.STLOC_ID,sl.IDENTIFIER,xsl.STORENAME,xsl.STORENUMBER,sl.ACTIVE," +
            "ROUND(SQRT( POWER(69.1 * (latitude - |LATITUDE|), 2) + POWER(69.1 * (|LONGITUDE| - longitude) * " +
            "COS(latitude / 57.3), 2))*|RADIUS_UOM|,14) AS distance,sl.LONGITUDE,sl.LATITUDE From stloc sl,xstloc xsl " +
            "Where sl.STOREENT_ID = '|STORECAS_ID|' AND sl.STLOC_ID = xsl.STLOC_ID  " +
            "AND sl.active IN (|IS_ACTIVE|) AND ROUND(SQRT( POWER(69.1 * (latitude - |LATITUDE|), 2) + POWER(69.1 * (|LONGITUDE| - longitude) * " +
            "COS(latitude / 57.3), 2))*|RADIUS_UOM|,14) < |RADIUS|  AND sl.STLOC_ID IN ( "),
    GET_PHYSICAL_STORES_FOR_BRAND(" Select STLOC_ID,IDENTIFIER from stloc Where STOREENT_ID = '|STORECAS_ID|' AND ACTIVE IN (|IS_ACTIVE|) "),
    GET_PHYSICAL_STORE_INFO_FOR_IDENTIFIER("  Select sl.stloc_id,xsl.STORENAME,xsl.STORENUMBER,sl.ACTIVE,sl.IDENTIFIER " +
            "  From stloc sl , xstloc xsl" +
            "  Where sl.STOREENT_ID = '|STORECAS_ID|' and sl.active in (|IS_ACTIVE|) AND sl.STLOC_ID = '|STLOC_ID|' AND xsl.stloc_id = sl.stloc_id "),
    GET_CONTACT_TYPE_CODE_NAME("select xt.CONTACT_CODE, xtd.NAME from XCONTACT_TYPE xt, XCONTACT_TYPEDESC xtd, STORE s where " +
            " xt.CONTACT_TYPE_ID = xtd.CONTACT_TYPE_ID and xtd.LANGUAGE_ID = s.LANGUAGE_ID and s.STORE_ID=|STORE_ID| order by xt.CONTACT_CODE"),
    GET_CONTACT_TYPE_CODE_NAME_BY_LOCALE("select xt.CONTACT_CODE, xtd.NAME from XCONTACT_TYPE xt, XCONTACT_TYPEDESC xtd, LANGUAGE l " +
            " where  xt.CONTACT_TYPE_ID = xtd.CONTACT_TYPE_ID and xtd.LANGUAGE_ID = l.LANGUAGE_ID and l.LOCALENAME='|LOCALENAME|' order by xt.CONTACT_CODE"),
    GET_SUPPORTED_LANGUAGES("select LANGUAGE_ID from STORELANG where STOREENT_ID=|STORE_ID|"),
    GET_CONTACT_TYPE_CODE_NAME_BY_LANGUAGE("select xt.CONTACT_CODE, xtd.NAME from XCONTACT_TYPE xt, XCONTACT_TYPEDESC xtd " +
            " where  xt.CONTACT_TYPE_ID = xtd.CONTACT_TYPE_ID and xtd.LANGUAGE_ID = |LANGUAGE_ID| order by xt.CONTACT_CODE"),
    GET_CONTACT_NAME_ID_NAME("select xn.CONTACT_NAME_ID, xnd.NAME from XCONTACT_NAME xn, XCONTACT_NAMEDESC xnd, STORE s where " +
            " xn.CONTACT_NAME_ID = xnd.CONTACT_NAME_ID and xnd.LANGUAGE_ID = s.LANGUAGE_ID and s.STORE_ID=|STORE_ID| order by xn.CONTACT_NAME_ID"),
    GET_CONTACT_NAME_ID_NAME_BY_LOCALE("select xn.CONTACT_NAME_ID, xnd.NAME from XCONTACT_NAME xn, XCONTACT_NAMEDESC xnd, LANGUAGE l " +
            " where  xn.CONTACT_NAME_ID = xnd.CONTACT_NAME_ID and xnd.LANGUAGE_ID = l.LANGUAGE_ID and l.LOCALENAME='|LOCALENAME|' order by xn.CONTACT_NAME_ID"),
    GET_CONTACT_NAME_ID_NAME_BY_LANGUAGE("select xn.CONTACT_NAME_ID, xnd.NAME from XCONTACT_NAME xn, XCONTACT_NAMEDESC xnd " +
            " where  xn.CONTACT_NAME_ID = xnd.CONTACT_NAME_ID and xnd.LANGUAGE_ID = |LANGUAGE_ID| order by xn.CONTACT_NAME_ID"),
    GET_PHYSICAL_STORE_INFO_BY_IDENTIFIER(" Select sl.stloc_id,xsl.STORENAME,xsl.STORENUMBER,sl.address1 line1,sl.address2 line2,sl.address3 line3,sl.city,sl.STATE,sl.zipcode postCode,sl.LATITUDE,sl.LONGITUDE, " +
            "  xsl.Countryabbr countryCode,sl.ACTIVE " +
            "  From stloc sl,xstloc xsl  Where sl.IDENTIFIER = '|IDENTIFIER|' " +
            "  and  sl.STLOC_ID = xsl.STLOC_ID "),
    GET_ATTRIBUTES_FOR_BRAND("  Select * From (select ATTR_ID,IDENTIFIER from attr ar where ar.STOREENT_ID = '|STORECAS_ID|' and ar.FIELD1 = 3) OFFSET |OFFSET| rows fetch next |NO_OF_ROWS| rows only"),
    GET_ATTRIBUTES_VALUES_FOR_ATTRIBUTE_WITH_BRAND(" Select arv.identifier from attrval arv Where arv.attr_id = '|ATTR_ID|' and arv.STOREENT_ID = '|STORECAS_ID|'"),
    GET_ATTRIBUTES_FOR_PHYSICAL_STORE_BY_IDENTIFIER("  Select distinct  sl.STLOC_ID , xsla.ATTR_ID,atr.IDENTIFIER,atr.FIELD3 attribute_type " +
            "   From STLOC sl,XSTLOCATTR xsla,ATTR atr " +
            "   WHERE sl.IDENTIFIER = '|IDENTIFIER|' AND sl.STLOC_ID = xsla.STLOC_ID AND atr.STOREENT_ID = sl.STOREENT_ID " +
            "   AND xsla.ATTR_ID = atr.ATTR_ID  AND atr.FIELD1 = 3  "),
    GET_ATTRIBUTE_VALUES_FOR_PHYSICAL_STORE_BY_IDENTIFIER(" Select distinct  sl.STLOC_ID , xsla.ATTR_ID,atrv.IDENTIFIER " +
            "     From STLOC sl,XSTLOCATTR xsla,ATTR atr,ATTRVAL atrv " +
            "     WHERE sl.IDENTIFIER = '|IDENTIFIER|' AND sl.STLOC_ID = xsla.STLOC_ID AND atr.STOREENT_ID = sl.STOREENT_ID " +
            "     AND xsla.ATTR_ID = atr.ATTR_ID  AND atr.FIELD1 = 3 AND atr.ATTR_ID = |ATTR_ID| " +
            "     AND atrv.STOREENT_ID = sl.STOREENT_ID  AND xsla.ATTRVAL_ID = atrv.ATTRVAL_ID "),
    GET_CONTACTS_FOR_PHYSICAL_STORE_BY_IDENTIFIER("  SELECT DISTINCT xsc.STLOC_ID,xsc.CONTACT_ID,xc.IDENTIFIER,xc.CONTACT_TYPE_ID,xct.CONTACT_CODE,CONTACT_NAME_ID,CONTACT_VALUE " +
            "    FROM STLOC sl,XSTLOC_CONTACT xsc,XCONTACT xc,XCONTACT_TYPE xct " +
            "    WHERE sl.IDENTIFIER='|IDENTIFIER|' AND sl.STLOC_ID = xsc.STLOC_ID AND xsc.CONTACT_ID = xc.CONTACT_ID AND xc.CONTACT_TYPE_ID = xct.CONTACT_TYPE_ID  "),
    GET_CONTACTS_BY_ROWS(" Select * From (SELECT DISTINCT xsc.CONTACT_ID,xc.IDENTIFIER,xc.CONTACT_TYPE_ID,xct.CONTACT_CODE,CONTACT_NAME_ID,CONTACT_VALUE " +
            "    FROM XSTLOC_CONTACT xsc,XCONTACT xc,XCONTACT_TYPE xct " +
            "    WHERE xsc.CONTACT_ID = xc.CONTACT_ID AND xc.CONTACT_TYPE_ID = xct.CONTACT_TYPE_ID) OFFSET |OFFSET| rows fetch next |NO_OF_ROWS| rows only "),
    GET_PHYSICAL_STORE_OPENING_DAYS_BY_IDENTIFIER(" SELECT distinct sl.STLOC_ID,xslh.DAY DAY_NUMBER " +
            "   FROM STLOC sl,XSTLOC xsl,XSTLOCHOURS xslh " +
            "   WHERE sl.IDENTIFIER = '|IDENTIFIER|' AND sl.STLOC_ID = xsl.STLOC_ID AND sl.STLOC_ID = xslh.STLOC_ID  order by DAY_NUMBER"),
    GET_CATALOG_ID_FOR_BRANDS(" Select CATALOG_ID,STOREENT_ID FROM STORECAT "),
    GET_CATEGORIES_FOR_CATALOG_ID("Select * From(Select distinct CATGROUP_ID_PARENT From CATGRPREL CGR WHERE CATALOG_ID = |CATALOG_D|) OFFSET |OFFSET| rows fetch next |NO_OF_ROWS| rows only"),
    GET_ORDERS_BY_USER_ID("select *  from ORDERS FULL OUTER JOIN ORDERITEMS ON  ORDERS.ORDERS_ID = ORDERITEMS.ORDERS_ID where ORDERS.MEMBER_ID = ?"),
    GET_CATALOG_ID("SELECT CATALOG_ID FROM CATALOG WHERE IDENTIFIER = '|IDENTIFIER|'"),
    GET_CATEGORY_ID_WITH_ATTRIBUTES("select distinct catgroup_id from xcatgroupattr where storeent_id = |STORE_ID| order by dbms_random.value" +
            "   fetch first 1 rows only "),
    GET_CATEGORY_ID_WITH_NO_ATTRIBUTES("select distinct c.catgroup_id from catgroup c, storeent s where s.storeent_id = |STORE_ID| and "+
            " c.member_id=s.member_id and c.catgroup_id not in (select catgroup_id from xcatgroupattr) order by dbms_random.value fetch first 1 rows only"),
    GET_CATEGORY_IDENTIFIER_WITH_ATTRIBUTES("select identifier from catgroup where catgroup_id = (select distinct catgroup_id from xcatgroupattr " +
            "where storeent_id = |STORE_ID| order by dbms_random.value fetch first 1 rows only)"),
    GET_CATEGORY_IDENTIFIER_WITH_NO_ATTRIBUTES("select identifier from catgroup where catgroup_id = (select distinct c.catgroup_id from catgroup c, storeent s where s.storeent_id = |STORE_ID| and " +
            "c.member_id=s.member_id and c.catgroup_id not in (select catgroup_id from xcatgroupattr) order by dbms_random.value fetch first 1 rows only)"),
    GET_PRODUCTS_FOR_CATEGORY_WITHOUT_PAGINATION(" SELECT CGR.CATGROUP_ID_CHILD,CGE.CATENTRY_ID,CE.MFPARTNUMBER,XCD.EXTENDEDNAME,CE.PARTNUMBER,CE.field4 From CATGRPREL CGR,CATGPENREL CGE,CATENTRY CE,XCATENTDESC XCD " +
            " WHERE CGR.CATALOG_ID = '|CATALOG_ID|' AND CGR.CATGROUP_ID_PARENT = '|CATEGORY_ID|' AND CGR.CATGROUP_ID_CHILD = CGE.CATGROUP_ID AND CGE.CATENTRY_ID = CE.CATENTRY_ID " +
            " AND CE.CATENTRY_ID = XCD.CATENTRY_ID AND XCD.LANGUAGE_ID = |LANGUAGE_ID| AND CE.CATENTTYPE_ID = 'ProductBean'"),
    GET_PRODUCTS_FOR_CATEGORY_WITH_PAGINATION(" Select * From( SELECT CGR.CATGROUP_ID_CHILD,CGE.CATENTRY_ID,CE.MFPARTNUMBER,XCD.EXTENDEDNAME,CE.PARTNUMBER,CE.field4 From CATGRPREL CGR,CATGPENREL CGE,CATENTRY CE,XCATENTDESC XCD " +
            " WHERE CGR.CATALOG_ID = '|CATALOG_ID|' AND CGR.CATGROUP_ID_PARENT = '|CATEGORY_ID|' AND CGR.CATGROUP_ID_CHILD = CGE.CATGROUP_ID AND CGE.CATENTRY_ID = CE.CATENTRY_ID " +
            " AND CE.CATENTRY_ID = XCD.CATENTRY_ID AND XCD.LANGUAGE_ID = |LANGUAGE_ID| AND CE.CATENTTYPE_ID = 'ProductBean') OFFSET |PAGE_NUMBER| rows fetch next |PAGE_SIZE| rows only ");


    private String query;

    private DatabaseQueries(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }

}
