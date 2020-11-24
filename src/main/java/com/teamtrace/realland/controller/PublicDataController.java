package com.teamtrace.realland.controller;

import com.teamtrace.realland.api.request.SearchCriteria;
import com.teamtrace.realland.search.city.CityListSearch;
import com.teamtrace.realland.search.district.DistrictListSearch;
import com.teamtrace.realland.search.propertyamenity.PropertyAmenityListSearch;
import com.teamtrace.realland.search.propertytype.PropertyTypeSearch;
import com.teamtrace.realland.search.province.ProvinceListSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("public_data")
public class PublicDataController extends AbstractController {
    @Autowired
    private PropertyTypeSearch propertyTypeSearch;
    @Autowired
    private ProvinceListSearch provinceListSearch;
    @Autowired
    private DistrictListSearch districtListSearch;
    @Autowired
    private CityListSearch cityListSearch;
    @Autowired
    private PropertyAmenityListSearch propertyAmenityListSearch;

    @RequestMapping(value = "property_types", method = RequestMethod.GET)
    public ResponseEntity<?> getPropertyTypeList(@RequestHeader HttpHeaders httpHeaders,
                                                 HttpServletResponse servletResponse) {
        SearchCriteria api = new SearchCriteria();

        return processDataSearchResponse(propertyTypeSearch.findByCriteria(api), servletResponse);
    }

    @RequestMapping(value = "provinces", method = RequestMethod.GET)
    public ResponseEntity<?> getProvinceList(@RequestHeader HttpHeaders httpHeaders,
                                             HttpServletResponse servletResponse) {
        SearchCriteria api = new SearchCriteria();

        return processDataSearchResponse(provinceListSearch.findByCriteria(api), servletResponse);
    }

    @RequestMapping(value = "districts/{provinceId}", method = RequestMethod.GET)
    public ResponseEntity<?> getDistrictList(@RequestHeader HttpHeaders httpHeaders,
                                             @PathVariable int provinceId,
                                             HttpServletResponse servletResponse) {
        SearchCriteria api = new SearchCriteria();
        api.setPK(provinceId);

        return processDataSearchResponse(districtListSearch.findByCriteria(api), servletResponse);
    }

    @RequestMapping(value = "cities/{districtId}", method = RequestMethod.GET)
    public ResponseEntity<?> getCityList(@RequestHeader HttpHeaders httpHeaders,
                                         @PathVariable int districtId,
                                         HttpServletResponse servletResponse) {
        SearchCriteria api = new SearchCriteria();
        api.setPK(districtId);

        return processDataSearchResponse(cityListSearch.findByCriteria(api), servletResponse);
    }

    @RequestMapping(value = "property_amenities/{typeId}", method = RequestMethod.GET)
    public ResponseEntity<?> getPropertyAmenityList(@RequestHeader HttpHeaders httpHeaders,
                                                    @PathVariable int typeId,
                                                    HttpServletResponse servletResponse) {
        SearchCriteria api = new SearchCriteria();
        api.setPK(typeId);

        return processDataSearchResponse(propertyAmenityListSearch.findByCriteria(api), servletResponse);
    }
}
