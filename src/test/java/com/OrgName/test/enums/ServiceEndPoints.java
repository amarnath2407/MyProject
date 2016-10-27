package com.salmon.test.enums;

/**
 * Created by akepativ on 27/07/2016.
 */
public enum ServiceEndPoints {

    LOCATION_DATA_BY_COUNTRY_LEVEL("wcs/resources/store/{storeId}/storelocator/locations/countries"),
    LOCATION_DATA_BY_COUNTRY_LEVEL_WITH_COUNTRY("wcs/resources/store/{storeId}/storelocator/locations/countries/{countryCode}"),
    LOCATION_DATA_BY_REGION_LEVEL("wcs/resources/store/{storeId}/storelocator/locations/regions"),
    LOCATION_DATA_BY_REGION_LEVEL_WITH_REGION("wcs/resources/store/{storeId}/storelocator/locations/regions/{regionCode}"),
    SEARCH_PHYSICAL_STORE("wcs/resources/store/{storeId}/storelocator/boutiques"),
    GET_PHYSICAL_STORE("wcs/resources/store/{storeId}/storelocator/boutiques/{boutiqueIdentifier}"),
    STORE_BY_LATITUDE_LONGITUDE("wcs/resources/store/{storeId}/storelocator/latitude/43.647/longitude/-79.379"),
    STORE_BY_LOCATION("wcs/resources/store/{storeId}/storelocator/byLocation"),
    STORE_BY_ID("wcs/resources/store/{storeId}/storelocator/byStoreId/{uniqueStoreId}"),
    GET_PHYSICAL_STORE_WITH_LATITUDE_AND_LONGITUDE("wcs/resources/store/{storeId}/storelocator/boutiques/long/{longitude}/lat/{latitude}"),
    GET_PHYSICAL_STORE_ATTRIBUTES("wcs/resources/store/{storeId}/storelocator/attributes"),
    GUEST_IDENTITY("wcs/resources/store/{storeId}/guestidentity"),
    PERSON("wcs/resources/store/{storeId}/person"),
    LOGIN_IDENTITY("wcs/resources/store/{storeId}/loginidentity"),
    POST_BOUTIQUE("wcs/resources/store/{storeIdentifier}/storelocator/boutiques"),
    GET_CONTACT_TYPES("wcs/resources/store/{storeId}/storelocator/contacttypes"),
    GET_CONTACT_NAMES("wcs/resources/store/{storeId}/storelocator/contactnames"),
    DELETE_BOUTIQUE("wcs/resources/store/{storeIdentifier}/storelocator/boutiques/{boutiqueIdentifier}"),
    PUT_BOUTIQUE("wcs/resources/store/{storeIdentifier}/storelocator/boutiques/{boutiqueIdentifier}"),
    GET_PRODUCTVIEW_BY_CATEGORY("wcs/resources/store/{storeIdentifier}/productview/byCategory/{categoryId}"),
    GET_TOP_CATEGORIES("search/resources/store/{storeId}/categoryview/@top"),
    GET_CATEGORY_DETAILS_FOR_IDENTIFIER("search/resources/store/{storeId}/categoryview/{categoryIdentifier}"),
    GET_CATEGORY_DETAILS_FOR_ID("search/resources/store/{storeId}/categoryview/byId/{categoryId}"),
    GET_CATEGORY_DETAILS_FOR_IDS("search/resources/store/{storeId}/categoryview/byIds"),
    GET_PRODUCTS_BY_CATEGORY("search/resources/store/{storeIdentifier}/productview/byCategory/{categoryId}"),

    // CORE MEDIA END POINTS
    GET_MEGAMENU("megamenu/brand/{brandName}/country/{countryName}/lang/{languageName}/restype={responseType}?countrygroup={countryGroupName}");

    private String url;

    private ServiceEndPoints(String url) {
        this.url = url;
    }
    public String getUrl(){
        return url;
    }
}
