import { ComponentFixture, TestBed } from '@angular/core/testing';

import { JuegoFormComponent } from './juego-form.component';

describe('JuegoFormComponent', () => {
  let component: JuegoFormComponent;
  let fixture: ComponentFixture<JuegoFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [JuegoFormComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(JuegoFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
