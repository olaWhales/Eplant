package com.whales.eplant.utility;

import com.whales.eplant.data.model.*;
import com.whales.eplant.dto.request.caterer.CatererAttributes;
import com.whales.eplant.dto.request.decorator.DecoratorAttributes;
import com.whales.eplant.dto.request.dj.DjAttributes;
import com.whales.eplant.dto.request.makeUp.MakeUpAttributes;
import com.whales.eplant.dto.request.mc.McAttributes;
import com.whales.eplant.dto.request.photographer.PhotographerAttributes;
import org.springframework.stereotype.Component;

@Component
public class RoleAttributeUtility {

    public static Photographer PhotographerDetails(PhotographerAttributes photographerAttributes, Photographer photographer) {
        if (photographerAttributes != null) {
            Photographer.builder()
                    .no_Of_photographer(photographerAttributes.getNo_Of_photographer())
                    .albumIncluded(photographerAttributes.isAlbumIncluded())
                    .droneIncluded(photographerAttributes.isDroneIncluded())
                    .deliveryTime(photographerAttributes.getDeliveryTime())
                    .build();
        }
        return photographer;
    }

    public static Caterer CatererDetails(CatererAttributes catererAttributes, Caterer caterer) {
        if (catererAttributes != null) {
            Caterer.builder()
                    .numberOfMeals(catererAttributes.getNumberOfMeals())
                    .menuOptions(catererAttributes.getMenuOptions())
                    .offersTasting(catererAttributes.isOffersTasting())
                    .deliveryIncluded(catererAttributes.isDeliveryIncluded())
                    .dietaryConsiderations(catererAttributes.getDietaryConsiderations())
                    .build();
        }
        return caterer;
    }

    public static Decorator DecoratorDetails(DecoratorAttributes decoratorAttributes, Decorator decorator) {
        if (decoratorAttributes != null) {
            Decorator.builder()
                    .customDesign(decoratorAttributes.isCustomDesign())
                    .flowersIncluded(decoratorAttributes.isFlowersIncluded())
                    .lightingIncluded(decoratorAttributes.isLightingIncluded())
                    .numberOfVenues(decoratorAttributes.getNumberOfVenues())
                    .themeOptions(decoratorAttributes.getThemeOptions())
                    .build();
        }
        return decorator;
    }

    public static MakeUp MakeUpDetails(MakeUpAttributes makeUpAttributes, MakeUp makeUp) {
        if (makeUpAttributes != null) {
            MakeUp.builder()
                    .makeupStyles(makeUpAttributes.getMakeupStyles())
                    .numberOfPeople(makeUpAttributes.getNumberOfPeople())
                    .offersTrialSession(makeUpAttributes.isOffersTrialSession())
                    .durationPerSession(makeUpAttributes.getDurationPerSession())
                    .productsUsed(makeUpAttributes.getProductsUsed())
                    .build();
        }
        return makeUp;
    }
    public static Mc McDetails(McAttributes mcAttributes , Mc mc){
        if(mcAttributes != null) {
            Mc.builder()
                    .eventTypeSpecialist(mcAttributes.getEventTypeSpecialist())
                    .languageOptions(mcAttributes.getLanguageOptions())
                    .dressCodeIncluded(mcAttributes.isDressCodeIncluded())
                    .performanceDuration(mcAttributes.getPerformanceDuration())
                    .build();
        }
        return mc ;
    }
    public static Dj DjDetails(DjAttributes djAttributes , Dj dj){
        if(djAttributes != null){
            Dj.builder()
                    .musicGenres(djAttributes.getMusicGenres())
                    .numberOfMicrophones(djAttributes.getNumberOfMicrophones())
                    .performanceDuration(djAttributes.getPerformanceDuration())
                    .lightingIncluded(djAttributes.isLightingIncluded())
                    .numberOfSpeakers(djAttributes.getNumberOfSpeakers())
                    .build();
        }
        return dj;
    }

}