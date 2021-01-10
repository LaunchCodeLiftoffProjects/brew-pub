package org.launchcode.brewpub;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.Validator;
import com.openpojo.validation.ValidatorBuilder;
import com.openpojo.validation.rule.impl.*;
import com.openpojo.validation.test.impl.DefaultValuesNullTester;
import com.openpojo.validation.test.impl.GetterTester;
import com.openpojo.validation.test.impl.SetterTester;
import org.junit.Before;
import org.junit.Test;
import org.launchcode.brewpub.models.*;
import org.launchcode.brewpub.models.dto.CreateAccountDTO;
import org.launchcode.brewpub.models.dto.NewPasswordDTO;

import java.util.ArrayList;
import java.util.List;


public class Pojo_UT {

    private String packageName = "org.launchcode.brewpub";
    private List<PojoClass> pojoClasses = new ArrayList<>();


    @Before
    public void setup() {
        pojoClasses.add(PojoClassFactory.getPojoClass(User.class));
        pojoClasses.add(PojoClassFactory.getPojoClass(Brew.class));
        pojoClasses.add(PojoClassFactory.getPojoClass(BrewReview.class));
        pojoClasses.add(PojoClassFactory.getPojoClass(Pub.class));
        pojoClasses.add(PojoClassFactory.getPojoClass(PubReview.class));
        pojoClasses.add(PojoClassFactory.getPojoClass(CreateAccountDTO.class));
        pojoClasses.add(PojoClassFactory.getPojoClass(NewPasswordDTO.class));
    }

    @Test
    public void validateClasses() {
        Validator validator = ValidatorBuilder.create()
                .with(new SetterMustExistRule(),
                        new GetterMustExistRule())
                .with(new SetterTester(),
                        new GetterTester())
                .with(new NoFieldShadowingRule())
                .with(new NoPublicFieldsExceptStaticFinalRule())
                .with(new NoNestedClassRule())
                .with(new DefaultValuesNullTester())
                .with(new NoPrimitivesRule())
                .with(new NoStaticExceptFinalRule())
                .build();
        validator.validate(pojoClasses);
    }

    @Test
    public void validateAbstract() {
        Validator validator = ValidatorBuilder.create()
                .with(new GetterMustExistRule())
                .with(new NoNestedClassRule())
                .with(new NoStaticExceptFinalRule())
                .with(new NoFieldShadowingRule())
                .with(new NoPublicFieldsExceptStaticFinalRule())
                .build();
        validator.validate(PojoClassFactory.getPojoClass(Review.class));
    }
}
