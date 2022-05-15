package com.xkball.flamereaction.util;

import com.xkball.flamereaction.material.elements.IElements;

/**
 * 元素周期表
 * 包含一个不存在的占位元素与所有已知元素
 * <p>
 * 枚举序数即为原子序数
 * <p>
 * 原子质量有小数点的较精确，没有的为放射性元素
 */
public enum PeriodicTableOfElements implements IElements {
    
    //占位元素，枚举序号0
    Rainbow("rainbow","RAB",UNKNOWN_MASS),
    
    //第一周期
    //氢
    H("hydrogen","H",1.0F),
    //氦
    He("helium","He",4.0F),
    
    //第二周期
    //锂
    Li("lithium","Li",6.9F),
    //铍
    Be("beryllium","Be",9.1F),
    //硼
    B("boron","B",10.8F),
    //碳
    C("carbon","C",12.0F),
    //氮
    N("nitrogen","N",14.0F),
    //氧
    O("oxygen","O",16.0F),
    //氟
    F("fluorine","F",19.0F),
    //氖
    Ne("neon","Ne",20.1F),
    
    //第三周期
    //钠
    Na("sodium","Na",22.9F),
    //镁
    Mg("magnesium","Mg",24.3F),
    //铝
    Al("aluminum","Al",26.9F),
    //硅
    Si("silicon","Si",28.0F),
    //磷
    P("phosphorus","P",30.9F),
    //硫
    S("sulfur","S",32.0F),
    //氯
    Cl("chlorine","Cl",35.5F),
    //氩
    Ar("argon","Ar",39.9F),
    
    //第四周期
    //钾
    K("potassium","K",39.1F),
    //钙
    Ca("calcium","Ca",40.0F),
    //钪
    Sc("scandium","Sc",44.9F),
    //钛
    Ti("titanium","Ti",47.8F),
    //钒
    V("vanadium","V",50.9F),
    //铬
    Cr("chromium","Cr",52.0F),
    //锰
    Mn("manganese","Mn",54.9F),
    //铁
    Fe("iron","Fe",55.8F),
    //钴
    Co("cobalt","Co",58.9F),
    //镍
    Ni("nickel","Ni",58.6F),
    //铜
    Cu("copper","Cu",64.5F),
    //锌
    Zn("zinc","Zn",65.4F),
    //镓
    Ga("gallium","Ga",69.7F),
    //锗
    Ge("germanium","Ge",72.6F),
    //砷
    As("arsenic","As",74.9F),
    //硒
    Se("selenium","Se",78.9F),
    //溴
    Br("bromine","Br",79.9F),
    //氪
    Kr("krypton","Kr",83.8F),
    
    //第五周期
    
    //铷
    Rb("rubidium","Rb",85.4F),
    //锶
    Sr("strontium","Sr",87.6F),
    //钇
    Y("yttrium","Y",88.9F),
    //锆
    Zr("zirconium","Zr",91.2F),
    //铌
    Nb("niobium","Nb",92.9F),
    //钼
    Mo("molybdenum","Mo",95.9F),
    //锝
    Tc("technetium","Tc",98F),
    //钌
    Ru("ruthenium","Ru",101.7F),
    //铑
    Rh("rhodium","Rh",102.9F),
    //钯
    Pd("palladium","Pd",106.4F),
    //银
    Ag("silver","Ag",107.8F),
    //镉
    Cd("cadmium","Cd",112.4F),
    //铟
    In("indium","In",114.8F),
    //锡
    Sn("tin","Sn",118.7F),
    //我居然漏掉51号，真的SB
    Sb("antimony","Sb",121.8F),
    //碲
    Te("tellurium","Te",127.6F),
    //碘
    I("iodine","I",126.9F),
    //氙
    Xe("xenon","Xe",131.2F),
    
    //第六周期
    //铯
    Cs("cesium","Cs",132.9F),
    //钡
    Ba("barium","Ba",137.3F),
    //镧系元素
    //镧
    La("lanthanum","La",138.9F),
    //铈
    Ce("cerium","Ce",140.1F),
    //镨
    Pr("praseodymium","Pr",140.9F),
    //钕
    Nd("neodymium","Nd",144.2F),
    //钷
    Pm("promethium","Pm",145F),
    //钐
    Sm("samarium","Sm",150.3F),
    //铕
    Eu("europium","Eu",151.9F),
    //钆
    Gd("gadolinium","Gd",157.2F),
    //铽
    Tb("terbium","Tb",158.9F),
    //镝
    Dy("dysprosium","Dy",162.5F),
    //钬
    Ho("holmium","Ho",164.9F),
    //铒
    Er("erbium","Er",167.3F),
    //铥
    Tm("thulium","Tm",168.9F),
    //镱
    Yd("ytterbium","Yd",173.0F),
    //镥
    Lu("lutetium","Lu",174.9F),
    
    //铪
    Hf("hafnium","Hf",178.5F),
    //钽
    Ta("tantalum","Ta",180.9F),
    //钨
    W("wolfram","E",183.8F),
    //铼
    Re("rhenium","Re",186.2F),
    //锇
    Os("osmium","Os",190.2F),
    //铱
    Ir("iridium","Ir",192.2F),
    //铂
    Pt("platinum","Pt",195.1F),
    //金
    Au("gold","Au",197.0F),
    //汞
    Hg("mercury","Hg",200.6F),
    //铊
    Tl("thallium","Tl",204.4F),
    //铅
    Pb("lead","Pb",207.2F),
    //铋
    Bi("bismuth","Bi",209.0F),
    //钋
    Po("polonium","Po",209F),
    //砹
    At("astatine","At",210F),
    //氡
    Rn("radon","Rn",222F),
    
    //第七周期
    //钫
    Fr("francium","Fr",223F),
    //镭
    Ra("radium","Ra",226F),
    //锕系元素
    //锕
    Ac("actinium","Ac",227F),
    //钍
    Th("thorium","Th",232.0F),
    //镤
    Pa("protactinium","Pa",231.0F),
    //铀
    U("uranium","U",238.0F),
    //镎
    Np("neptunium","Np",237F),
    //钚
    Pu("plutonium","Pu",244F),
    //镅
    Am("americium","Am",243F),
    //锔
    Cm("curium","Cm",247F),
    //锫
    Bk("beryllium","Bk",247F),
    //锎
    Cf("californium","Cf",251F),
    //锿
    Es("einsteinium","Es",252F),
    //镄
    Fm("fermium","Fm",257F),
    //钔
    Md("mendelevium","Md",258F),
    //锘
    No("nobelium","No",259F),
    //铹
    Lr("lawrencium","Lr",262F),
    
    //钅卢
    Rf("rutherfordium","Rf",267F),
    //钅杜
    Db("dubnium","Db",270F),
    //钅喜
    Sg("seaborgium","Sg",269F),
    //钅波
    Bh("bohrium","Bh",270F),
    //钅黑
    Hs("hassium","Hs",270F),
    //钅麦
    Mt("meitnerium","Mt",278F),
    //钅达  鐽
    Ds("darmstadtium","Ds",281F),
    //钅仑
    Rg("roentgenium","Rg",281F),
    //鎶
    Cn("copernicium","Cn",285F),
    //钅尔
    Nh("nihonium","Nh",286F),
    //鈇
    Fl("flerovium","Fl",289F),
    //镆
    Mc("moscovium ","Mc",289F),
    //钅立
    Lv("livermorium","Lv",293F),
    //石田
    Ts("tennessine ","Ts",293F),
    //气奥
    Og("oganesson","Og",294F);
    
    
    //名称
    private final String elementName;
    //元素符号
    private final String elementSymbol;
    //相对原子质量
    private final float relativeAtomicMass;
    
    PeriodicTableOfElements(String elementName,String elementSymbol,float relativeAtomicMass) {
        this.elementName = elementName;
        this.elementSymbol = elementSymbol;
        this.relativeAtomicMass = relativeAtomicMass;
    }
    
    public float getRelativeAtomicMass() {
        return relativeAtomicMass;
    }
    public String getElementName() {
        return elementName;
    }
    
    public String getElementSymbol() {
        return elementSymbol;
    }
    
    @Override
    public boolean isNature() {
        return this.ordinal()==0;
    }
    
    @Override
    public String getName() {
        return this.getElementName();
    }
}
