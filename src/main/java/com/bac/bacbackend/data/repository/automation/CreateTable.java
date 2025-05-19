package com.bac.bacbackend.data.repository.automation;

public enum CreateTable {
    CREATE_TABLE("""
            CREATE FUNCTION set_updated_at() RETURNS TRIGGER AS $$
            BEGIN
              NEW.updated_at = now();
              RETURN NEW;
            END;
            $$ LANGUAGE plpgsql;
        
            create table if not exists scraper_objects (
                website varchar(255) not null
                constraint scraper_objects_pk
                primary key,
                url varchar(255) not null
                constraint scraper_objects_unique
                unique,
                txtlocator varchar not null,
                txthref varchar not null,
                imglocator varchar not null,
                imghref varchar not null,
                title varchar(255) not null,
                sum varchar(255) not null,
                updated_at timestamp default now()
            );

            alter table scraper_objects owner to postgres;
            
            DO $$
            BEGIN
                IF NOT EXISTS (
                    SELECT 1 FROM pg_trigger
                    WHERE tgname = 'trigger_set_updated_at'
                ) THEN
                    CREATE TRIGGER trigger_set_updated_at
                    BEFORE UPDATE ON scraper_objects
                    FOR EACH ROW
                    EXECUTE FUNCTION set_updated_at();
                END IF;
            END
            $$;
            
            insert into scraper_objects (
                website,
                url,
                txtlocator,
                txthref,
                imglocator,
                imghref,
                title,
                sum
            )
            values
                ('aljazeera', 'https://www.aljazeera.com/africa/', 'section#news-feed-container h3 a', 'href', '.gc__image-wrap .responsive-image', 'src', 'h1', 'p.article__subhead'),
                ('sky', 'https://www.skynews.com.au/australia-news', 'h4 a', 'href', 'div.responsive-img', 'innerHTML', 'h1', '#story-intro'),
                ('france24', 'https://www.france24.com/en/europe', '.article__title a', 'href', '.m-item-list-article__wrapper picture', 'innerHTML', 'h1', '.t-content__chapo'),
                ('dpa', 'https://www.dpa-international.com/general-news/', '.card-content a', 'href', 'figure.image a img', 'src', 'h1', '.main'),
                ('ap', 'https://apnews.com/hub/latin-america', 'div.PagePromo-media a.Link[aria-label]', 'href', 'div.PagePromo-media a.Link[aria-label] picture[data-crop]', 'innerHTML', 'h1', '.RichTextStoryBody p'),
                ('allafrica', 'https://allafrica.com/', '.foundation a', 'href', '.img-responsive', 'src', '.topic h1', '.topic p'),
                ('aljazeera_middle_east', 'https://www.aljazeera.com/middle-east/', 'section#news-feed-container h3 a', 'href', '.gc__image-wrap .responsive-image', 'src', 'h1', 'p.article__subhead'),
                ('nrk', 'https://www.nrk.no/norge/', 'div.article.widget a', 'href', 'div.responsive-img', 'innerHTML', 'h1', 'div[data-main-content]'),
                ('agenciaBrasil', 'https://agenciabrasil.ebc.com.br/en/ultimas', 'a.capa-noticia', 'href', 'a.capa-noticia img', 'src', 'h1', '.conteudo-noticia p'),
                ('reuters_americas', 'https://www.reuters.com/world/americas/', '.story-card a[class*=''media-story-card'']', 'href', '.story-card .image', 'innerHTML', 'h1', 'div[data-testid^=''paragraph-0'']'),
                ('reuters', 'https://www.reuters.com/world/', '.story-card a[class*=''media-story-card'']', 'href', '.story-card .image', 'innerHTML', 'h1', 'div[data-testid^=''paragraph-0'']'),
                ('aap_pacific', 'https://aapnews.aap.com.au/topics/pacific-island-nations', 'div[itemprop=itemListElement] a', 'href', '.story-card a[class*=''media-story-card'']', 'innerHTML', 'h1', '.article-content p'),
                ('an', 'https://asianews.network/category/home-page/daily-digest/', 'div.post-img a', 'href', 'div.post-img a', 'innerHTML', 'h1.entry-title', 'div.entry-content'),
                ('diplomat_central_asia', 'https://thediplomat.com/regions/central-asia/', '.td-post', 'href', '.td-post', 'innerHTML', 'h1', '#td-lead')
            ON CONFLICT (website) DO NOTHING
            """
    );

    private final String sql;

    CreateTable(String sql) { this.sql = sql; }
    public String getSql() { return sql; }
}
