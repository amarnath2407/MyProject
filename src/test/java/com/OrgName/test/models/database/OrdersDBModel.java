package com.salmon.test.models.database;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * Created by Developer on 09/10/2016.
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrdersDBModel {

    String ORDERS_ID;
    String ORMORDER;
    String ORGENTITY_ID;

    String TOTALTAX;
    String  TOTALSHIPPING;
    String TOTALTAXSHIPPING;

    String LOCKED;
    String  TIMEPLACED;
    String  SEQUENCE;
    String  PROVIDERORDERNUM;
    String SHIPASCOMPLETE;
    String  FIELD3;
    String  ORDCHNLTYP_ID;
    String  NOTIFICATIONID;
    String TYPE;
    String  EDITOR_ID;
    String BUSCHN_ID;
    String  SOURCEID;
    String EXPIREDATE;
    String  BLOCKED;
    String OPSYSTEM_ID;
    String  TRANSFERSTATUS;
    String BUYERPO_ID;
    String  OPTCOUNTER;

//    ====================== ORDER ITEMS
    String ORDERITEMS_ID;
    String STOREENT_ID;

    String TERMCOND_ID;
    String TRADING_ID;
    String ITEMSPC_ID;
    String CATENTRY_ID;
    String PARTNUM;
    String SHIPMODE_ID;
    String FFMCENTER_ID;
    String MEMBER_ID;
    String ADDRESS_ID;
    String ALLOCADDRESS_ID;
    String PRICE;
    String LINEITEMTYPE;
    String STATUS;
    String OUTPUTQ_ID;
    String INVENTORYSTATUS;
    String LASTCREATE;
    String LASTUPDATE;
    String FULFILLMENTSTATUS;
    String LASTALLOCUPDATE;
    String OFFER_ID;
    String TIMERELEASED;
    String TIMESHIPPED;
    String CURRENCY;
    String COMMENTS;
    String TOTALPRODUCT;
    String QUANTITY;
    String TAXAMOUNT;
    String TOTALADJUSTMENT;
    String SHIPTAXAMOUNT;
    String ESTAVAILTIME;
    String FIELD1;
    String DESCRIPTION;
    String FIELD2;
    String ALLOCATIONGROUP;
    String SHIPCHARGE;
    String BASEPRICE;
    String BASECURRENCY;
    String TRACKNUMBER;
    String TRACKDATE;
    String PREPAREFLAGS;
    String CORRELATIONGROUP;
    String PROMISEDAVAILTIME;
    String SHIPPINGOFFSET;
    String NEEDEDQUANTITY;
    String  ALLOCQUANTITY;
    String ALLOCFFMC_ID;
    String  ORDRELEASENUM;
    String CONFIGURATIONID;
    String  SUPPLIERDATA;
    String SUPPLIERPARTNUMBER;
    String  AVAILQUANTITY;
    String ISEXPEDITED;
    String REQUESTEDSHIPDATE;
    String TIECODE;

}
